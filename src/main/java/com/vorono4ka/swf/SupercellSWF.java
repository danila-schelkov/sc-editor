package com.vorono4ka.swf;

import com.vorono4ka.compression.Compressor;
import com.vorono4ka.compression.Decompressor;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import com.vorono4ka.flatloader.FlatByteStream;
import com.vorono4ka.flatloader.FlatLoader;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.exceptions.*;
import com.vorono4ka.swf.flat.FlatSupercellSWF;
import com.vorono4ka.swf.flat.TextureSet;
import com.vorono4ka.swf.flat.roots.*;
import com.vorono4ka.swf.originalObjects.*;
import com.vorono4ka.utilities.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

public class SupercellSWF {
    public static final String TEXTURE_EXTENSION = "_tex.sc";

    private static final IntFunction<int[]> TRIANGULATOR_FUNCTION_1 = (triangleCount) -> {
        int[] indices = new int[triangleCount * 3];
        for (int i = 0; i < triangleCount; i++) {
            indices[i * 3] = 0;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = i + 2;
        }
        return indices;
    };

    private static final IntFunction<int[]> TRIANGULATOR_FUNCTION_2 = (triangleCount) -> {
        int[] indices = new int[triangleCount * 3];
        for (int i = 0; i < triangleCount; i++) {
            indices[i * 3] = i;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = i + 2;
        }
        return indices;
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(SupercellSWF.class);

    private final List<String> fontsNames = new ArrayList<>();
    private final List<ScMatrixBank> matrixBanks = new ArrayList<>();

    private Export[] exports;

    private SWFTexture[] textures = new SWFTexture[0];
    private ShapeOriginal[] shapes;
    private MovieClipOriginal[] movieClips;
    private TextFieldOriginal[] textFields;

    private List<MovieClipModifierOriginal> movieClipModifiers;

    private boolean isHalfScalePossible;
    private boolean useUncommonResolution;
    private boolean useExternalTexture;
    private String uncommonResolutionTexturePath;

    private String filename;
    private Path path;

    public boolean load(String filepath, String filename) throws LoadingFaultException, UnableToFindObjectException, UnsupportedCustomPropertyException, TextureFileNotFound {
        this.filename = filename;
        this.path = Path.of(filepath);

        if (this.loadInternal(filepath, false)) {
            if (!this.useExternalTexture) return true;

            if (this.useUncommonResolution) {
                filepath = this.uncommonResolutionTexturePath;
            } else {
                filepath = filepath.substring(0, filepath.length() - 3) + TEXTURE_EXTENSION;
            }

            return this.loadInternal(filepath, true);
        }

        return false;
    }

    private boolean loadInternal(String path, boolean isTextureFile) throws LoadingFaultException, UnableToFindObjectException, UnsupportedCustomPropertyException, TextureFileNotFound {
        byte[] decompressedData;
        int version;

        try {
            byte[] data;
            try (FileInputStream fis = new FileInputStream(path)) {
                data = fis.readAllBytes();
            } catch (IOException e) {
                throw new TextureFileNotFound(path);
            }

            DataInputStream stream = Decompressor.createDataInputStreamFromBytes(data);

            Decompressor.checkMagic(stream);

            version = Decompressor.parseVersion(stream);

            decompressedData = Decompressor.decompress(stream, data, version);
        } catch (UnknownFileMagicException | UnknownFileVersionException |
                 IOException exception) {
            LOGGER.error("An error occurred while decompressing the file: {}", path, exception);
            return false;
        }

        if (version == 0x05000000) {
            return loadAsFlat(path, decompressedData);
        }

        return loadOld(path, isTextureFile, decompressedData);
    }

    public boolean loadAsFlat(String path, byte[] decompressedData) {
        this.path = Path.of(path);
        FlatLoader flatLoader = new FlatLoader(new FlatByteStream(decompressedData));
        FlatSupercellSWF flatSwf = flatLoader.deserializeClass(FlatSupercellSWF.class);

        ResourceRoot resourceRoot = flatSwf.getResourceRoot();
        List<String> strings = resourceRoot.getStrings();

        for (ScMatrixBank matrixBank : resourceRoot.getMatrixBanks()) {
            this.addMatrixBank(matrixBank);
        }

        ExportRoot exportRoot = flatSwf.getExportRoot();
        List<Short> exportIds = exportRoot.getExportIds();
        List<Integer> exportNameReferenceIds = exportRoot.getExportNameReferenceIds();

        int exportCount = exportIds.size();
        this.exports = new Export[exportCount];
        for (int i = 0; i < exportCount; i++) {
            Short id = exportIds.get(i);
            String name = strings.get(exportNameReferenceIds.get(i));
            this.exports[i] = new Export(id, name);
        }

        this.textFields = flatSwf.getTextFieldRoot().getTextFields().toArray(TextFieldOriginal[]::new);
        for (TextFieldOriginal textField : this.textFields) {
            textField.resolveStrings(strings);
        }

        List<ShapePoint> shapePoints = resourceRoot.getShapePoints();

        ShapeRoot shapeRoot = flatSwf.getShapeRoot();
        List<ShapeOriginal> shapes = shapeRoot.getShapes();
        for (ShapeOriginal shape : shapes) {
            for (ShapeDrawBitmapCommand command : shape.getCommands()) {
                ShapePoint[] points = new ShapePoint[command.getPointCount()];

                for (int i = 0; i < points.length; i++) {
                    points[i] = shapePoints.get(command.getStartingPointIndex() + i);
                }

                command.setPoints(points);
                command.setTriangulator(TRIANGULATOR_FUNCTION_2);
            }
        }

        this.shapes = shapes.toArray(ShapeOriginal[]::new);

        MovieClipRoot movieClipRoot = flatSwf.getMovieClipRoot();
        List<MovieClipOriginal> movieClips = movieClipRoot.getMovieClips();

        this.movieClips = movieClips.toArray(MovieClipOriginal[]::new);
        for (MovieClipOriginal movieClip : this.movieClips) {
            movieClip.resolveStrings(strings);
            movieClip.resolveReferences(resourceRoot.getScalingGrids(), resourceRoot.getMovieClipFrameElements());
        }

        // Note: copying to allow editing
        MovieClipModifierRoot movieClipModifierRoot = flatSwf.getMovieClipModifierRoot();
        if (movieClipModifierRoot.getModifiers() != null) {
            this.movieClipModifiers = new ArrayList<>(movieClipModifierRoot.getModifiers());
        }

        List<TextureSet> textureSets = flatSwf.getTextureRoot().getTextureSets();
        this.textures = new SWFTexture[textureSets.size()];

        for (int i = 0; i < textureSets.size(); i++) {
            TextureSet textureSet = textureSets.get(i);

            SWFTexture texture = null;

            SWFTexture lowresTexture = textureSet.getLowresTexture();
            if (lowresTexture != null) {
                lowresTexture.resolveStrings(strings);
                texture = lowresTexture;
            }

            SWFTexture highresTexture = textureSet.getHighresTexture();
            if (highresTexture != null) {
                highresTexture.resolveStrings(strings);
                texture = highresTexture;
            }

            if (texture == null) {
                throw new IllegalStateException("Texture cannot be null! File " + path);
            }

            texture.resolve();
            texture.setIndex(i);

            this.textures[i] = texture;
        }

        return false;
    }

    private boolean loadOld(String path, boolean isTextureFile, byte[] decompressedData) throws LoadingFaultException, UnsupportedCustomPropertyException, TextureFileNotFound, UnableToFindObjectException {
        ByteStream stream = new ByteStream(decompressedData);

        if (isTextureFile) {
            return this.loadTags(stream, true, path);
        }

        int shapeCount = stream.readShort();
        int movieClipCount = stream.readShort();
        int textureCount = stream.readShort();
        int textFieldCount = stream.readShort();
        int matrixCount = stream.readShort();
        int colorTransformCount = stream.readShort();

        ScMatrixBank matrixBank = new ScMatrixBank();
        matrixBank.init(matrixCount, colorTransformCount);
        this.addMatrixBank(matrixBank);

        stream.skip(5);

        int exportCount = stream.readShort();

        short[] exportIds = stream.readShortArray(exportCount);

        String[] exportNames = new String[exportCount];
        for (int i = 0; i < exportCount; i++) {
            exportNames[i] = stream.readAscii();
        }

        this.exports = new Export[exportCount];
        for (int i = 0; i < exports.length; i++) {
            exports[i] = new Export(exportIds[i], exportNames[i]);
        }

        this.shapes = new ShapeOriginal[shapeCount];
        ArrayUtils.fill(this.shapes, ShapeOriginal::new);

        this.movieClips = new MovieClipOriginal[movieClipCount];
        ArrayUtils.fill(this.movieClips, MovieClipOriginal::new);

        this.textures = new SWFTexture[textureCount];
        ArrayUtils.fill(this.textures, SWFTexture::new);

        this.textFields = new TextFieldOriginal[textFieldCount];
        ArrayUtils.fill(this.textFields, TextFieldOriginal::new);

        if (this.loadTags(stream, false, path)) {
            for (Export export : exports) {
                MovieClipOriginal movieClip = this.getOriginalMovieClip(export.id(), export.name());
                movieClip.setExportName(export.name());
            }

            for (ShapeOriginal shape : shapes) {
                for (ShapeDrawBitmapCommand command : shape.getCommands()) {
                    command.setTriangulator(TRIANGULATOR_FUNCTION_1);
                }
            }

            return true;
        }

        return false;
    }

    private boolean loadTags(ByteStream stream, boolean isTextureFile, String path) throws LoadingFaultException, UnsupportedCustomPropertyException, TextureFileNotFound {
        String highresSuffix = "_highres";
        String lowresSuffix = "_lowres";

        ScMatrixBank matrixBank = this.matrixBanks.get(0);

        int loadedShapes = 0;
        int loadedMovieClips = 0;
        int loadedTextures = 0;
        int loadedTextFields = 0;
        int loadedMatrices = 0;
        int loadedColorTransforms = 0;

        int loadedMovieClipsModifiers = 0;

        while (true) {
            int tag = stream.readUnsignedChar();
            int length = stream.readInt();

            if (length < 0) {
                throw new NegativeTagLengthException(String.format("Negative tag length. Tag %d, %s", tag, this.filename));
            }

            if (tag > Tag.values().length) {
                try {
                    throw new UnsupportedTagException(String.format("Encountered unknown tag %d, %s", tag, this.filename));
                } catch (UnsupportedTagException exception) {
                    LOGGER.error("An error occurred while loading the file: {}", path, exception);
                }

                if (length > 0) {
                    stream.skip(length);
                    continue;
                }
            }

            Tag tagValue = Tag.values()[tag];
            switch (tagValue) {
                case EOF -> {
                    if (isTextureFile) {
                        if (loadedTextures != this.textures.length) {
                            throw new LoadingFaultException(String.format("Texture count in .sc and _tex.sc doesn't match: %s", this.filename));
                        }
                    } else {
                        if (loadedMatrices != matrixBank.getMatrixCount() ||
                            loadedColorTransforms != matrixBank.getColorTransformCount() ||
                            loadedMovieClips != this.movieClips.length ||
                            loadedShapes != this.shapes.length ||
                            loadedTextFields != this.textFields.length) {
                            throw new LoadingFaultException("Didn't load whole .sc properly. ");
                        }
                    }

                    return true;
                }
                case TEXTURE, TEXTURE_2, TEXTURE_3, TEXTURE_4, TEXTURE_5, TEXTURE_6,
                     TEXTURE_7, TEXTURE_8, KHRONOS_TEXTURE,
                     COMPRESSED_KHRONOS_TEXTURE -> {
                    if (loadedTextures >= this.textures.length) {
                        throw new TooManyObjectsException("Trying to load too many textures from ");
                    }
                    this.textures[loadedTextures].setIndex(loadedTextures);
                    this.textures[loadedTextures++].load(stream, tagValue, !this.useExternalTexture || isTextureFile);
                }
                case SHAPE, SHAPE_2 -> {
                    if (loadedShapes >= this.shapes.length) {
                        throw new TooManyObjectsException("Trying to load too many shapes from ");
                    }

                    this.shapes[loadedShapes++].load(stream, tagValue, filename);
                }
                case MOVIE_CLIP, MOVIE_CLIP_2, MOVIE_CLIP_3, MOVIE_CLIP_4, MOVIE_CLIP_5,
                     MOVIE_CLIP_6 -> {
                    if (loadedMovieClips >= this.movieClips.length) {
                        throw new TooManyObjectsException("Trying to load too many MovieClips from ");
                    }

                    this.movieClips[loadedMovieClips++].load(stream, tagValue, filename);
                }
                case TEXT_FIELD, TEXT_FIELD_2, TEXT_FIELD_3, TEXT_FIELD_4, TEXT_FIELD_5,
                     TEXT_FIELD_6, TEXT_FIELD_7, TEXT_FIELD_8, TEXT_FIELD_9 -> {
                    if (loadedTextFields >= this.textFields.length) {
                        throw new TooManyObjectsException("Trying to load too many TextFields from ");
                    }

                    this.textFields[loadedTextFields++].load(stream, tagValue, this::readFontName);
                }
                case MATRIX ->
                    matrixBank.getMatrix(loadedMatrices++).load(stream, false);
                case COLOR_TRANSFORM ->
                    matrixBank.getColorTransform(loadedColorTransforms++).read(stream);
                case TAG_TIMELINE_INDEXES -> {
                    try {
                        throw new UnsupportedTagException("TAG_TIMELINE_INDEXES no longer in use");
                    } catch (UnsupportedTagException exception) {
                        LOGGER.error("An error occurred while loading the file: {}", path, exception);
                    }

                    int indicesLength = stream.readInt();
                    stream.skip(indicesLength);
                }
                case HALF_SCALE_POSSIBLE -> this.isHalfScalePossible = true;
                case USE_EXTERNAL_TEXTURE -> this.useExternalTexture = true;
                case USE_UNCOMMON_RESOLUTION -> {
                    this.useUncommonResolution = true;

                    String withoutExtension = path.substring(0, path.length() - 3);
                    String highresPath = withoutExtension + highresSuffix + TEXTURE_EXTENSION;
                    String lowresPath = withoutExtension + lowresSuffix + TEXTURE_EXTENSION;

                    this.isHalfScalePossible = true;
                    String uncommonPath = highresPath;
                    if (!ResourceManager.doesFileExist(highresPath)) {
                        if (ResourceManager.doesFileExist(lowresPath)) {
                            uncommonPath = lowresPath;
                        }
                    }

                    this.uncommonResolutionTexturePath = uncommonPath;
                }
                case EXTERNAL_FILES_SUFFIXES -> {
                    highresSuffix = stream.readAscii();
                    lowresSuffix = stream.readAscii();
                }
                case MATRIX_PRECISE ->
                    matrixBank.getMatrix(loadedMatrices++).load(stream, true);
                case MOVIE_CLIP_MODIFIERS -> {
                    int movieClipModifierCount = stream.readShort();

                    this.movieClipModifiers = new ArrayList<>(movieClipModifierCount);
                    for (int i = 0; i < movieClipModifierCount; i++) {
                        this.movieClipModifiers.add(new MovieClipModifierOriginal());
                    }
                }
                case MODIFIER_STATE_2, MODIFIER_STATE_3, MODIFIER_STATE_4 ->
                    this.movieClipModifiers.get(loadedMovieClipsModifiers++).load(stream, tagValue);
                case EXTRA_MATRIX_BANK -> {
                    int matrixCount = stream.readShort();
                    int colorTransformCount = stream.readShort();

                    matrixBank = new ScMatrixBank();
                    matrixBank.init(matrixCount, colorTransformCount);
                    this.matrixBanks.add(matrixBank);

                    loadedMatrices = 0;
                    loadedColorTransforms = 0;
                }
                default -> {
                    // TODO: add strict mode which crashes on errors and probably enable it by default
                    // TODO: also add properties and settings for the app
                    try {
                        throw new UnsupportedTagException(String.format("Encountered unknown tag %d, %s", tag, this.filename));
                    } catch (UnsupportedTagException exception) {
                        LOGGER.error("An error occurred while loading the file: {}", path, exception);
                    }

                    if (length > 0) {
                        stream.skip(length);
                    }
                }
            }
        }
    }

    public void save(String path) {
        this.saveInternal(path, false);
        // Add an option "Save textures as external files" when saving the whole project
    }

    private void saveInternal(String path, boolean isTextureFile) {
        ByteStream stream = new ByteStream();

        if (!isTextureFile) {
            saveObjectsInfo(stream);
        }

        this.saveTags(stream);

        byte[] data = stream.getData();

        try {
            data = Compressor.compress(data, 4);
        } catch (IOException | UnknownFileVersionException e) {
            throw new RuntimeException(e);
        }

        File file = new File(path);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException exception) {
            LOGGER.error("An error occurred while saving the file: {}", path, exception);
        }
    }

    private void saveObjectsInfo(ByteStream stream) {
        stream.writeShort(this.shapes.length);
        stream.writeShort(this.movieClips.length);
        stream.writeShort(this.textures.length);
        stream.writeShort(this.textFields.length);
        stream.writeShort(this.matrixBanks.get(0).getMatrixCount());
        stream.writeShort(this.matrixBanks.get(0).getColorTransformCount());

        stream.write(new byte[5]);  // unused

        stream.writeShort(this.exports.length);
        for (Export export : exports) {
            stream.writeShort(export.id());
        }

        for (Export export : exports) {
            stream.writeAscii(export.name());
        }
    }

    private void saveTags(ByteStream stream) {
        List<Savable> savables = this.getSavableObjects();

        for (Savable object : savables) {
            stream.writeBlock(object.getTag(), object::save);
        }

        stream.writeBlock(Tag.EOF, (ignored) -> {
        });
    }

    private List<Savable> getSavableObjects() {
        List<Savable> objects = new ArrayList<>();

        if (this.isHalfScalePossible()) {
            objects.add(new Savable() {
                @Override
                public void save(ByteStream stream) {

                }

                @Override
                public Tag getTag() {
                    return Tag.HALF_SCALE_POSSIBLE;
                }
            });
        }

        if (this.useExternalTexture) {
            objects.add(new Savable() {
                @Override
                public void save(ByteStream stream) {

                }

                @Override
                public Tag getTag() {
                    return Tag.USE_EXTERNAL_TEXTURE;
                }
            });
        }

        objects.addAll(List.of(this.textures));
        objects.addAll(List.of(this.shapes));
        for (int i = 0; i < this.matrixBanks.size(); i++) {
            ScMatrixBank matrixBank = this.matrixBanks.get(i);

            if (i != 0) {
                objects.add(new Savable() {
                    @Override
                    public void save(ByteStream stream) {
                        stream.writeShort(matrixBank.getMatrixCount());
                        stream.writeShort(matrixBank.getColorTransformCount());
                    }

                    @Override
                    public Tag getTag() {
                        return Tag.EXTRA_MATRIX_BANK;
                    }
                });
            }

            objects.addAll(matrixBank.getMatrices());
            objects.addAll(matrixBank.getColorTransforms());
        }
        objects.addAll(List.of(this.textFields));
        objects.addAll(List.of(this.movieClips));

        if (this.movieClipModifiers != null && !this.movieClipModifiers.isEmpty()) {
            objects.add(new Savable() {
                @Override
                public void save(ByteStream stream) {
                    stream.writeShort(movieClipModifiers.size());
                }

                @Override
                public Tag getTag() {
                    return Tag.MOVIE_CLIP_MODIFIERS;
                }
            });

            objects.addAll(this.movieClipModifiers);
        }

        return objects;
    }

    public MovieClipOriginal getOriginalMovieClip(int id, String name) throws UnableToFindObjectException {
        for (MovieClipOriginal movieClip : this.movieClips) {
            if (movieClip.getId() == id) {
                return movieClip;
            }
        }

        String message = String.format("Unable to find some MovieClip id from %s", this.filename);
        if (name != null) {
            message += String.format(" needed by export name %s", name);
        }

        throw new UnableToFindObjectException(message);
    }

    public DisplayObjectOriginal getOriginalDisplayObject(int id, String name) throws UnableToFindObjectException {
        for (ShapeOriginal shape : this.shapes) {
            if (shape.getId() == id) {
                return shape;
            }
        }

        for (MovieClipOriginal movieClip : this.movieClips) {
            if (movieClip.getId() == id) {
                return movieClip;
            }
        }

        for (TextFieldOriginal textField : textFields) {
            if (textField.getId() == id) {
                return textField;
            }
        }

        for (MovieClipModifierOriginal movieClipModifier : movieClipModifiers) {
            if (movieClipModifier.getId() == id) {
                return movieClipModifier;
            }
        }

        String message = String.format("Unable to find some DisplayObject id %d, %s", id, this.filename);
        if (name != null) {
            message += String.format(" needed by export name %s", name);
        }

        throw new UnableToFindObjectException(message);
    }

    public int getMovieClipCount() {
        return this.movieClips.length;
    }

    public int getTextureCount() {
        return this.textures.length;
    }

    public Integer[] getShapesIds() {
        return Arrays.stream(shapes).map(DisplayObjectOriginal::getId).toArray(Integer[]::new);
    }

    public Integer[] getMovieClipsIds() {
        return Arrays.stream(movieClips).map(DisplayObjectOriginal::getId).toArray(Integer[]::new);
    }

    public Integer[] getTextFieldsIds() {
        return Arrays.stream(textFields).map(DisplayObjectOriginal::getId).toArray(Integer[]::new);
    }

    public ScMatrixBank getMatrixBank(int index) {
        return this.matrixBanks.get(index);
    }

    public void addMatrixBank(ScMatrixBank matrixBank) {
        this.matrixBanks.add(matrixBank);
    }

    public SWFTexture getTexture(int textureIndex) {
        return this.textures[textureIndex];
    }

    public String getFilename() {
        return filename;
    }

    public void addModifier(MovieClipModifierOriginal modifierOriginal) {
        movieClipModifiers.add(modifierOriginal);
    }

    /**
     * @return path, containing a filename
     */
    public Path getPath() {
        return path;
    }

    public String readFontName(ByteStream stream) {
        String fontName = stream.readAscii();
        if (fontName != null) {
            if (!this.fontsNames.contains(fontName)) {
                this.fontsNames.add(fontName);
            }
        }

        return fontName;
    }

    public boolean isHalfScalePossible() {
        return isHalfScalePossible;
    }

    public List<ShapeDrawBitmapCommand> getDrawBitmapsOfTexture(int textureIndex) {
        List<ShapeDrawBitmapCommand> bitmapCommands = new ArrayList<>();

        for (ShapeOriginal shape : this.shapes) {
            for (ShapeDrawBitmapCommand command : shape.getCommands()) {
                if (command.getTextureIndex() == textureIndex) {
                    if (!bitmapCommands.contains(command)) {
                        bitmapCommands.add(command);
                    }
                }
            }
        }

        return bitmapCommands;
    }
}

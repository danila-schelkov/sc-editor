package com.vorono4ka.swf;

import com.vorono4ka.compression.Compressor;
import com.vorono4ka.compression.Decompressor;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.exceptions.*;
import com.vorono4ka.swf.originalObjects.*;
import com.vorono4ka.utilities.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupercellSWF {
    public static final String TEXTURE_EXTENSION = "_tex.sc";
    public static final byte[] START_SECTION_BYTES = {'S', 'T', 'A', 'R', 'T'};

    private static final Logger LOGGER = LoggerFactory.getLogger(SupercellSWF.class);

    private final List<String> fontsNames = new ArrayList<>();
    private final List<ScMatrixBank> matrixBanks = new ArrayList<>();

    private int shapeCount;
    private int movieClipCount;
    private int textureCount;
    private int textFieldCount;

    private Export[] exports;

    private SWFTexture[] textures = new SWFTexture[0];
    private ShapeOriginal[] shapes;
    private MovieClipOriginal[] movieClips;
    private TextFieldOriginal[] textFields;

    private MovieClipModifierOriginal[] movieClipModifiers;

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
        byte[] data;

        try (FileInputStream fis = new FileInputStream(path)) {
            data = fis.readAllBytes();
            int startSectionIndex = ArrayUtils.indexOf(data, START_SECTION_BYTES);
            if (startSectionIndex != -1) {
                data = Arrays.copyOf(data, startSectionIndex);
            }
        } catch (IOException e) {
            throw new TextureFileNotFound(path);
        }

        byte[] decompressedData;

        try {
            decompressedData = Decompressor.decompress(data);
        } catch (UnknownFileMagicException | UnknownFileVersionException |
                 IOException exception) {
            LOGGER.error("An error occurred while decompressing the file: {}", path, exception);
            return false;
        }

        ByteStream stream = new ByteStream(decompressedData);

        if (isTextureFile) {
            return this.loadTags(stream, true, path);
        }

        this.shapeCount = stream.readShort();
        this.movieClipCount = stream.readShort();
        this.textureCount = stream.readShort();
        this.textFieldCount = stream.readShort();
        int matrixCount = stream.readShort();
        int colorTransformCount = stream.readShort();

        ScMatrixBank matrixBank = new ScMatrixBank();
        matrixBank.init(matrixCount, colorTransformCount);
        this.matrixBanks.add(matrixBank);

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

        this.shapes = new ShapeOriginal[this.shapeCount];
        ArrayUtils.fill(this.shapes, ShapeOriginal::new);

        this.movieClips = new MovieClipOriginal[this.movieClipCount];
        ArrayUtils.fill(this.movieClips, MovieClipOriginal::new);

        this.textures = new SWFTexture[this.textureCount];
        ArrayUtils.fill(this.textures, SWFTexture::new);

        this.textFields = new TextFieldOriginal[this.textFieldCount];
        ArrayUtils.fill(this.textFields, TextFieldOriginal::new);

        if (this.loadTags(stream, false, path)) {
            for (Export export : exports) {
                MovieClipOriginal movieClip = this.getOriginalMovieClip(export.id() & 0xFFFF, export.name());
                movieClip.setExportName(export.name());
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
                        if (loadedTextures != this.textureCount) {
                            throw new LoadingFaultException(String.format("Texture count in .sc and _tex.sc doesn't match: %s", this.filename));
                        }
                    } else {
                        if (loadedMatrices != matrixBank.getMatrixCount() ||
                            loadedColorTransforms != matrixBank.getColorTransformCount() ||
                            loadedMovieClips != this.movieClipCount ||
                            loadedShapes != this.shapeCount ||
                            loadedTextFields != this.textFieldCount) {
                            throw new LoadingFaultException("Didn't load whole .sc properly. ");
                        }
                    }

                    return true;
                }
                case TEXTURE, TEXTURE_2, TEXTURE_3, TEXTURE_4, TEXTURE_5, TEXTURE_6,
                     TEXTURE_7, TEXTURE_8, KHRONOS_TEXTURE,
                     COMPRESSED_KHRONOS_TEXTURE -> {
                    if (loadedTextures >= this.textureCount) {
                        throw new TooManyObjectsException("Trying to load too many textures from ");
                    }
                    this.textures[loadedTextures].setIndex(loadedTextures);
                    this.textures[loadedTextures++].load(stream, tagValue, !this.useExternalTexture || isTextureFile, this.path.getParent());
                }
                case SHAPE, SHAPE_2 -> {
                    if (loadedShapes >= this.shapeCount) {
                        throw new TooManyObjectsException("Trying to load too many shapes from ");
                    }

                    this.shapes[loadedShapes++].load(stream, tagValue, this::getTexture, filename);
                }
                case MOVIE_CLIP, MOVIE_CLIP_2, MOVIE_CLIP_3, MOVIE_CLIP_4, MOVIE_CLIP_5,
                     MOVIE_CLIP_6 -> {
                    if (loadedMovieClips >= this.movieClipCount) {
                        throw new TooManyObjectsException("Trying to load too many MovieClips from ");
                    }

                    this.movieClips[loadedMovieClips++].load(stream, tagValue, filename);
                }
                case TEXT_FIELD, TEXT_FIELD_2, TEXT_FIELD_3, TEXT_FIELD_4, TEXT_FIELD_5,
                     TEXT_FIELD_6, TEXT_FIELD_7, TEXT_FIELD_8, TEXT_FIELD_9 -> {
                    if (loadedTextFields >= this.textFieldCount) {
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
                    this.movieClipModifiers = new MovieClipModifierOriginal[movieClipModifierCount];
                    ArrayUtils.fill(this.movieClipModifiers, MovieClipModifierOriginal::new);
                }
                case MODIFIER_STATE_2, MODIFIER_STATE_3, MODIFIER_STATE_4 ->
                    this.movieClipModifiers[loadedMovieClipsModifiers++].load(stream, tagValue);
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

        stream.writeBlock(Tag.EOF, (ignored) -> {});
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

        if (this.movieClipModifiers != null && this.movieClipModifiers.length > 0) {
            objects.add(new Savable() {
                @Override
                public void save(ByteStream stream) {
                    stream.writeShort(movieClipModifiers.length);
                }

                @Override
                public Tag getTag() {
                    return Tag.MOVIE_CLIP_MODIFIERS;
                }
            });

            objects.addAll(List.of(this.movieClipModifiers));
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

    public SWFTexture getTexture(int textureIndex) {
        return this.textures[textureIndex];
    }

    public String getFilename() {
        return filename;
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

package com.vorono4ka.swf;

import com.vorono4ka.compression.Decompressor;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tags;
import com.vorono4ka.swf.displayObjects.original.*;
import com.vorono4ka.swf.exceptions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupercellSWF {
    public static final String TEXTURE_EXTENSION = "_tex.sc";
    private ByteStream stream;

    private int shapesCount;
    private int movieClipsCount;
    private int texturesCount;
    private int textFieldsCount;
    private int movieClipModifiersCount;

    private List<ScMatrixBank> matrixBanks;

    private int exportsCount;
    private int[] exportsIds;
    private String[] exportsNames;

    private int[] shapesIds;
    private int[] movieClipsIds;
    private int[] textFieldsIds;

    private ShapeOriginal[] shapes;
    private MovieClipOriginal[] movieClips;
    private SWFTexture[] textures;
    private TextFieldOriginal[] textFields;

    private MovieClipModifierOriginal[] movieClipModifiers;

    private boolean useLowresTexture;
    private boolean useUncommonResolution;
    private boolean useExternalTexture;
    private boolean useHighresInsteadLowres;
    private String uncommonResolutionTexturePath;

    private String filename;

    public SupercellSWF() {
        this.matrixBanks = new ArrayList<>();
    }

    public boolean load(String path, String filename) throws TooManyObjectsException, NegativeTagLengthException, LoadingFaultException, UnableToFindObjectException {
        this.filename = filename;

        if (this.loadInternal(path, false)) {
            if (!this.useExternalTexture) return true;

            if (this.useUncommonResolution) {
                path = this.uncommonResolutionTexturePath;
            } else {
                path = path.substring(0, path.length() - 3) + TEXTURE_EXTENSION;
            }

            return this.loadInternal(path, true);
        }

        return false;
    }

    private boolean loadInternal(String path, boolean isTextureFile) throws TooManyObjectsException, NegativeTagLengthException, LoadingFaultException, UnableToFindObjectException {
        byte[] data;

        File file = new File(path);

        try {
            data = new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        byte[] decompressedData;

        try {
            decompressedData = Decompressor.decompress(data);
        } catch (UnknownFileMagicException | UnknownFileVersionException | IOException e) {
            e.printStackTrace();
            return false;
        }

        this.stream = new ByteStream(decompressedData);

        if (isTextureFile) {
            return this.loadTags(true, path);
        }

        this.shapesCount = this.stream.readInt16();
        this.movieClipsCount = this.stream.readInt16();
        this.texturesCount = this.stream.readInt16();
        this.textFieldsCount = this.stream.readInt16();
        int matricesCount = this.stream.readInt16();
        int colorTransformsCount = this.stream.readInt16();

        ScMatrixBank matrixBank = new ScMatrixBank();
        matrixBank.init(matricesCount, colorTransformsCount);
        this.matrixBanks.add(matrixBank);

        this.stream.skip(5);

        this.exportsCount = this.stream.readInt16();
        this.exportsIds = this.stream.readShortArray(this.exportsCount);

        this.exportsNames = new String[this.exportsCount];
        for (int i = 0; i < this.exportsCount; i++) {
            this.exportsNames[i] = this.stream.readAscii();
        }

        this.shapesIds = new int[this.shapesCount];
        this.movieClipsIds = new int[this.movieClipsCount];
        this.textFieldsIds = new int[this.textFieldsCount];

        this.shapes = new ShapeOriginal[this.shapesCount];
        for (int i = 0; i < this.shapesCount; i++) {
            this.shapes[i] = new ShapeOriginal();
        }

        this.movieClips = new MovieClipOriginal[this.movieClipsCount];
        for (int i = 0; i < this.movieClipsCount; i++) {
            this.movieClips[i] = new MovieClipOriginal();
        }

        this.textures = new SWFTexture[this.texturesCount];
        for (int i = 0; i < this.texturesCount; i++) {
            this.textures[i] = new SWFTexture();
        }

        this.textFields = new TextFieldOriginal[this.textFieldsCount];
        for (int i = 0; i < this.textFieldsCount; i++) {
            this.textFields[i] = new TextFieldOriginal();
        }

        if (this.loadTags(false, path)) {
            if (this.exportsCount == 0) return true;

            for (int i = 0; i < this.exportsCount; i++) {
                String exportName = this.exportsNames[i];
                MovieClipOriginal movieClip = this.getOriginalMovieClip(this.exportsIds[i], exportName);
                movieClip.setName(exportName);
            }

            return true;
        }
        return false;
    }

    private boolean loadTags(boolean isTexture, String path) throws TooManyObjectsException, NegativeTagLengthException, LoadingFaultException {
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
            int tag = this.stream.readInt8();
            int length = this.stream.readInt32();

            if (length < 0) {
                throw new NegativeTagLengthException("Negative tag length. Tag " + tag);
            }

            switch (Tags.values()[tag]) {
                case EOF -> {
                    if (isTexture) {
                        if (loadedTextures != this.texturesCount) {
                            throw new LoadingFaultException(String.format("Texture count in .sc and _tex.sc doesn't match: %s", this.filename));
                        }
                    } else {
                        if (loadedMatrices != matrixBank.getMatricesCount() ||
                            loadedColorTransforms != matrixBank.getColorTransformsCount() ||
                            loadedMovieClips != this.movieClipsCount ||
                            loadedShapes != this.shapesCount ||
                            loadedTextFields != this.textFieldsCount) {
                            throw new LoadingFaultException("Didn't load whole .sc properly. ");
                        }
                    }

                    return true;
                }
                case TEXTURE_1, TEXTURE_16, TEXTURE_19, TEXTURE_24, TEXTURE_27, TEXTURE_28, TEXTURE_29, TEXTURE_34 -> {
                    if (loadedTextures >= this.texturesCount) {
                        throw new TooManyObjectsException("Trying to load too many textures from ");
                    }
                    this.textures[loadedTextures++].load(this, tag);

                    this.stream.skip(length);
                }
                case SHAPE_2, SHAPE_18 -> {
                    if (loadedShapes >= this.shapesCount) {
                        throw new TooManyObjectsException("Trying to load too many shapes from ");
                    }
                    this.shapesIds[loadedShapes] = this.shapes[loadedShapes++].load(this, tag);

                    this.stream.skip(length);
                }
                case MOVIE_CLIP_3, MOVIE_CLIP_10, MOVIE_CLIP_12, MOVIE_CLIP_14, MOVIE_CLIP_35 -> {
                    if (loadedMovieClips >= this.movieClipsCount) {
                        throw new TooManyObjectsException("Trying to load too many MovieClips from ");
                    }
                    this.movieClipsIds[loadedMovieClips] = this.movieClips[loadedMovieClips++].load(this, tag);

                    this.stream.skip(length);
                }
                case TEXT_FIELD_7, TEXT_FIELD_15, TEXT_FIELD_20, TEXT_FIELD_21, TEXT_FIELD_25, TEXT_FIELD_WITH_OUTLINE, TEXT_FIELD_43, TEXT_FIELD_44 -> {
                    if (loadedTextFields >= this.textFieldsCount) {
                        throw new TooManyObjectsException("Trying to load too many TextFields from ");
                    }
                    this.textFieldsIds[loadedTextFields] = this.textFields[loadedTextFields++].load(this, tag);

                    this.stream.skip(length);
                }
                case MATRIX -> matrixBank.getMatrix(loadedMatrices++).read(this.stream);
                case COLOR_TRANSFORM -> matrixBank.getColorTransforms(loadedColorTransforms++).read(this.stream);
                case TAG_TIMELINE_INDEXES -> {
                    try {
                        throw new UnsupportedTagException("TAG_TIMELINE_INDEXES no longer in use");
                    } catch (UnsupportedTagException e) {
                        e.printStackTrace();
                    }

                    int indexesLength = this.stream.readInt32();
                    this.stream.skip(indexesLength);
                }
                case USE_LOWRES_TEXTURE -> this.useLowresTexture = true;
                case USE_EXTERNAL_TEXTURE -> this.useExternalTexture = true;
                case USE_UNCOMMON_RESOLUTION -> {
                    this.useLowresTexture = true;
                    this.useUncommonResolution = true;

                    String withoutExtension = path.substring(0, path.length() - 3);
                    String highresPath = withoutExtension + highresSuffix + TEXTURE_EXTENSION;
                    String lowresPath = withoutExtension + lowresSuffix + TEXTURE_EXTENSION;

                    String uncommonPath = lowresPath;
                    if (!ResourceManager.doesFileExist(lowresPath)) {
                        if (ResourceManager.doesFileExist(highresPath)) {
                            uncommonPath = highresPath;
                            this.useHighresInsteadLowres = true;
                        }
                    }

                    this.uncommonResolutionTexturePath = uncommonPath;
                }
                case EXTERNAL_FILES_SUFFIXES -> {
                    highresSuffix = this.stream.readAscii();
                    lowresSuffix = this.stream.readAscii();
                }
                case MATRIX_PRECISE -> matrixBank.getMatrix(loadedMatrices++).readPrecise(this.stream);
                case MOVIE_CLIP_MODIFIERS -> {
                    this.movieClipModifiersCount = this.stream.readInt16();
                    this.movieClipModifiers = new MovieClipModifierOriginal[this.movieClipModifiersCount];

                    for (int i = 0; i < this.movieClipModifiersCount; i++) {
                        this.movieClipModifiers[i] = new MovieClipModifierOriginal();
                    }
                }
                case MODIFIER_STATE_2, MODIFIER_STATE_3, MODIFIER_STATE_4 -> {
                    this.movieClipModifiers[loadedMovieClipsModifiers++].load(this, tag);
                }
                case EXTRA_MATRIX_BANK -> {
                    int matricesCount = this.stream.readInt16();
                    int colorTransformsCount = this.stream.readInt16();

                    matrixBank = new ScMatrixBank();
                    matrixBank.init(matricesCount, colorTransformsCount);
                    this.matrixBanks.add(matrixBank);

                    loadedMatrices = 0;
                    loadedColorTransforms = 0;
                }
                default -> {
                    try {
                        throw new UnsupportedTagException(String.format("Encountered unknown tag %d, %s", tag, this.filename));
                    } catch (UnsupportedTagException exception) {
                        exception.printStackTrace();
                    }

                    if (length > 0) {
                        this.stream.skip(length);
                    }
                }
            }
        }
    }

    private MovieClipOriginal getOriginalMovieClip(int id, String name) throws UnableToFindObjectException {
        for (int i = 0; i < this.movieClipsCount; i++) {
            if (this.movieClipsIds[i] == id) {
                return this.movieClips[i];
            }
        }
        
        String message = String.format("Unable to find some MovieClip id from %s", this.filename);
        if (name != null) {
            message += String.format(" needed by export name %s", name);
        }

        throw new UnableToFindObjectException(message);
    }

    private DisplayObjectOriginal getOriginalDisplayObject(int id, String name) throws UnableToFindObjectException {
        for (int i = 0; i < this.shapesCount; i++) {
            if (this.shapesIds[i] == id) {
                return this.shapes[i];
            }
        }

        for (int i = 0; i < this.movieClipsCount; i++) {
            if (this.movieClipsIds[i] == id) {
                return this.movieClips[i];
            }
        }

        for (int i = 0; i < this.textFieldsCount; i++) {
            if (this.textFieldsIds[i] == id) {
                return this.textFields[i];
            }
        }

        for (int i = 0; i < this.movieClipModifiersCount; i++) {
            if (this.movieClipModifiers[i].getId() == id) {
                return this.movieClipModifiers[i];
            }
        }

        String message = String.format("Unable to find some DisplayObject id %d, %s", id, this.filename);
        if (name != null) {
            message += String.format(" needed by export name %s", name);
        }

        throw new UnableToFindObjectException(message);
    }

    public ByteStream getStream() {
        return stream;
    }
}

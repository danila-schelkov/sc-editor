package com.vorono4ka.swf.flat;

import com.vorono4ka.flatloader.annotations.FlatChunk;
import com.vorono4ka.swf.flat.roots.*;

public class FlatSupercellSWF {
    @FlatChunk
    private ResourceRoot resourceRoot;
    @FlatChunk
    private ExportRoot exportRoot;
    @FlatChunk
    private TextFieldRoot textFieldRoot;
    @FlatChunk
    private ShapeRoot shapeRoot;
    @FlatChunk
    private MovieClipRoot movieClipRoot;
    @FlatChunk
    private MovieClipModifierRoot movieClipModifierRoot;
    @FlatChunk
    private TextureRoot textureRoot;

    public ResourceRoot getResourceRoot() {
        return resourceRoot;
    }

    public ExportRoot getExportRoot() {
        return exportRoot;
    }

    public TextFieldRoot getTextFieldRoot() {
        return textFieldRoot;
    }

    public ShapeRoot getShapeRoot() {
        return shapeRoot;
    }

    public MovieClipRoot getMovieClipRoot() {
        return movieClipRoot;
    }

    public MovieClipModifierRoot getMovieClipModifierRoot() {
        return movieClipModifierRoot;
    }

    public TextureRoot getTextureRoot() {
        return textureRoot;
    }
}

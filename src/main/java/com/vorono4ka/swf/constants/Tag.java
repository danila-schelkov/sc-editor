package com.vorono4ka.swf.constants;

public enum Tag {
    EOF,
    TEXTURE,
    SHAPE,
    MOVIE_CLIP,
    SHAPE_DRAW_BITMAP_COMMAND,
    MOVIE_CLIP_FRAME,
    SHAPE_DRAW_COLOR_FILL_COMMAND,
    TEXT_FIELD,
    MATRIX,
    COLOR_TRANSFORM,
    MOVIE_CLIP_2,
    MOVIE_CLIP_FRAME_2,
    MOVIE_CLIP_3,
    TAG_TIMELINE_INDEXES,
    MOVIE_CLIP_4,
    TEXT_FIELD_2,
    TEXTURE_2,  // TEXTURE with mipmaps
    SHAPE_DRAW_BITMAP_COMMAND_2,
    SHAPE_2,
    TEXTURE_3,
    TEXT_FIELD_3,
    // if TextField tag >= TEXT_FIELD_4, it has an outline
    TEXT_FIELD_4,
    SHAPE_DRAW_BITMAP_COMMAND_3,
    HALF_SCALE_POSSIBLE,
    TEXTURE_4,
    TEXT_FIELD_5,
    USE_EXTERNAL_TEXTURE,
    // Textures separated by tiles (interlaced)
    TEXTURE_5,
    TEXTURE_6,
    TEXTURE_7,  // TEXTURE_6 with mipmaps
    USE_UNCOMMON_RESOLUTION,
    SCALING_GRID,
    EXTERNAL_FILES_SUFFIXES,
    TEXT_FIELD_6,
    TEXTURE_8,
    MOVIE_CLIP_5,
    MATRIX_PRECISE,
    MOVIE_CLIP_MODIFIERS,
    // StencilRenderingState
    /**
     * All next frame children are the mask
     */
    MODIFIER_STATE_2,
    /**
     * All next frame children are masked
     */
    MODIFIER_STATE_3,
    /**
     * Mask cleaning
     */
    MODIFIER_STATE_4,
    MATRIX_BANK_INDEX,
    EXTRA_MATRIX_BANK,
    TEXT_FIELD_7,
    TEXT_FIELD_8,
    /**
     * Contains KTX file data
     */
    KHRONOS_TEXTURE,
    TEXT_FIELD_9,
    /**
     * Contains a filename of the compressed with zstd texture "zktx"
     */
    COMPRESSED_KHRONOS_TEXTURE,
    UNKNOWN_48,  // probably, custom properties for movie clips
    /**
     * Has custom properties
     */
    MOVIE_CLIP_6,
    ;

    public boolean hasBlendData() {
        return this == MOVIE_CLIP_3 || this == MOVIE_CLIP_5 || this == MOVIE_CLIP_6;
    }

    public boolean hasCustomProperties() {
        return this == Tag.MOVIE_CLIP_6;
    }

    public int getTextureFilter() {
        return switch (this) {
            case TEXTURE, TEXTURE_4, TEXTURE_5, TEXTURE_6 -> 1;
            case TEXTURE_2, TEXTURE_3, TEXTURE_7 -> 2;
            case TEXTURE_8, KHRONOS_TEXTURE, COMPRESSED_KHRONOS_TEXTURE -> 0;
            default ->
                throw new IllegalStateException("Unsupported texture tag: " + this);
        };
    }
}

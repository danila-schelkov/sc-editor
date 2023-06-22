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
    TEXT_FIELD_4,  // if TextField tag >= TEXT_FIELD_4, it has an outline
    SHAPE_DRAW_BITMAP_COMMAND_3,
    HALF_SCALE_POSSIBLE,
    TEXTURE_4,
    TEXT_FIELD_5,
    USE_EXTERNAL_TEXTURE,
    TEXTURE_5,  // Textures separated by tales
    TEXTURE_6,
    TEXTURE_7,  // TEXTURE_6 with mipmaps
    USE_UNCOMMON_RESOLUTION,
    SCALING_GRID,
    EXTERNAL_FILES_SUFFIXES,
    TEXT_FIELD_6,
    TEXTURE_8,
    MOVIE_CLIP_35,
    MATRIX_PRECISE,
    MOVIE_CLIP_MODIFIERS,
    MODIFIER_STATE_2,  // StencilRenderingState  // all next frame children are the mask
    MODIFIER_STATE_3,  // all next frame children are masked
    MODIFIER_STATE_4,  // mask cleaning
    MATRIX_BANK_INDEX,
    EXTRA_MATRIX_BANK,
    TEXT_FIELD_7,
    TEXT_FIELD_8,
    KHRONOS_TEXTURE,
    TEXT_FIELD_9
}

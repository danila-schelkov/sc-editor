package com.vorono4ka.swf;

import com.vorono4ka.flatloader.SerializeType;
import com.vorono4ka.flatloader.annotations.FlatType;

public record MovieClipFrameElement(
    @FlatType(value = SerializeType.INT16, isUnsigned = true) int childIndex,
    @FlatType(value = SerializeType.INT16, isUnsigned = true) int matrixIndex,
    @FlatType(value = SerializeType.INT16, isUnsigned = true) int colorTransformIndex
) {
}

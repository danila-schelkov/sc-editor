package com.vorono4ka.swf.flat;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.originalObjects.SWFTexture;

@VTableClass
public class TextureSet {
    @VTableField(0)
    private SWFTexture lowresTexture;
    @VTableField(1)
    private SWFTexture highresTexture;

    public SWFTexture getLowresTexture() {
        return lowresTexture;
    }

    public SWFTexture getHighresTexture() {
        return highresTexture;
    }
}

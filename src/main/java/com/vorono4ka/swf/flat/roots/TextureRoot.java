package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.flat.TextureSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class TextureRoot {
    @VTableField(0)
    private ArrayList<TextureSet> textureSets;

    public List<TextureSet> getTextureSets() {
        return Collections.unmodifiableList(textureSets);
    }
}

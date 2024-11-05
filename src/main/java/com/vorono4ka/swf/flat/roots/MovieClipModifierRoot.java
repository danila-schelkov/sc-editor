package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.originalObjects.MovieClipModifierOriginal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class MovieClipModifierRoot {
    @VTableField(0)
    private ArrayList<MovieClipModifierOriginal> modifiers;

    public List<MovieClipModifierOriginal> getModifiers() {
        return modifiers != null ? Collections.unmodifiableList(modifiers) : null;
    }
}

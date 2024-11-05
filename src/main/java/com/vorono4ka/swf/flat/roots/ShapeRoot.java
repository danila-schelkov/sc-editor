package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.originalObjects.ShapeOriginal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class ShapeRoot {
    @VTableField(0)
    private ArrayList<ShapeOriginal> shapes;

    public List<ShapeOriginal> getShapes() {
        return Collections.unmodifiableList(shapes);
    }
}

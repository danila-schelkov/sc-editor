package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.originalObjects.TextFieldOriginal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class TextFieldRoot {
    @VTableField(0)
    private ArrayList<TextFieldOriginal> textFields;

    public List<TextFieldOriginal> getTextFields() {
        return Collections.unmodifiableList(textFields);
    }
}

package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class ExportRoot {
    @VTableField(0)
    private ArrayList<Short> exportIds;
    @VTableField(1)
    private ArrayList<Integer> exportNameReferenceIds;

    public List<Short> getExportIds() {
        return Collections.unmodifiableList(exportIds);
    }

    public List<Integer> getExportNameReferenceIds() {
        return Collections.unmodifiableList(exportNameReferenceIds);
    }
}

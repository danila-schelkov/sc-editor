package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.CustomStructureSize;
import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.originalObjects.ShapePoint;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.MovieClipFrameElement;
import com.vorono4ka.swf.ScMatrixBank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class ResourceRoot {
    @VTableField(0)
    private ArrayList<String> strings;
    @VTableField(3)
    private ArrayList<Rect> scalingGrids;
    @VTableField(4)
    @CustomStructureSize(3)
    private ArrayList<MovieClipFrameElement> movieClipFrameElements;
    @VTableField(5)
    @CustomStructureSize(10)
    private ArrayList<ShapePoint> shapePoints;
    @VTableField(6)
    private ArrayList<ScMatrixBank> matrixBanks;

    public List<String> getStrings() {
        return Collections.unmodifiableList(strings);
    }

    public List<Rect> getScalingGrids() {
        return Collections.unmodifiableList(scalingGrids);
    }

    public List<MovieClipFrameElement> getMovieClipFrameElements() {
        return Collections.unmodifiableList(movieClipFrameElements);
    }

    public List<ShapePoint> getShapePoints() {
        return Collections.unmodifiableList(shapePoints);
    }

    public List<ScMatrixBank> getMatrixBanks() {
        return Collections.unmodifiableList(matrixBanks);
    }
}

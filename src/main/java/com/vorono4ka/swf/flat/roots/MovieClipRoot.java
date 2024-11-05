package com.vorono4ka.swf.flat.roots;

import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.swf.originalObjects.MovieClipOriginal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VTableClass
public class MovieClipRoot {
    @VTableField(0)
    private ArrayList<MovieClipOriginal> movieClips;

    public List<MovieClipOriginal> getMovieClips() {
        return Collections.unmodifiableList(movieClips);
    }
}

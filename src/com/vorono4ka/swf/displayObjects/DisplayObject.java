package com.vorono4ka.swf.displayObjects;

import com.jogamp.opengl.GL3;

public abstract class DisplayObject {
    public abstract void render(GL3 gl);

    public abstract String getTypeName();
}

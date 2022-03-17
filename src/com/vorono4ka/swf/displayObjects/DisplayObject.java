package com.vorono4ka.swf.displayObjects;

import com.jogamp.opengl.GL2;

public abstract class DisplayObject {
    public abstract void render(GL2 gl);

    public abstract String getTypeName();
}

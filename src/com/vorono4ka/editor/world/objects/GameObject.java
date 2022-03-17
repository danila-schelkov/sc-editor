package com.vorono4ka.editor.world.objects;

import com.jogamp.opengl.GL2;

public abstract class GameObject {
    public abstract void render(GL2 gl);

    public abstract String getTypeName();
}

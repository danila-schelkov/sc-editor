package dev.donutquine.editor.renderer.shader;

import java.nio.FloatBuffer;

public abstract class Shader {
    protected final Attribute[] attributes;

    public Shader(Attribute... attributes) {
        this.attributes = attributes;
    }

    public abstract void bind();

    public abstract void unbind();

    public abstract void delete();

    public abstract void setUniformMatrix4f(String uniformName, FloatBuffer matrixBuffer);

    public Attribute[] getAttributes() {
        return attributes;
    }
}

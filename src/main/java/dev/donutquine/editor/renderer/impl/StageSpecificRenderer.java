package dev.donutquine.editor.renderer.impl;

import dev.donutquine.editor.renderer.Renderer;

public interface StageSpecificRenderer extends Renderer {
    public void addVertex(float x, float y, float u, float v);

    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd);
}

package com.vorono4ka.editor.renderer.impl;

import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Framebuffer;
import com.vorono4ka.editor.renderer.gl.GLFramebuffer;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;

public final class RendererHelper {
    private RendererHelper() {}

    public static Framebuffer prepareStageForRendering(EditorStage stage, Rect bounds) {
        Camera camera = stage.getCamera();
        camera.reset();
        camera.init((int) bounds.getWidth(), (int) bounds.getHeight());
        camera.moveToFit(bounds);

        stage.updatePMVMatrix();

        Framebuffer framebuffer = new GLFramebuffer(stage.getGlContext(), (int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()));
        stage.getRenderer().setViewport(0, 0, framebuffer.getWidth(), framebuffer.getHeight());
        return framebuffer;
    }

    public static void rollbackRenderer(EditorStage stage, ReadonlyRect viewport) {
        stage.doInRenderThread(() -> {
            stage.getRenderer().setViewport(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());
            stage.getCamera().init(viewport.getLeft(), viewport.getTop(), viewport.getRight(), viewport.getBottom());

            stage.getCamera().reset();
            stage.updatePMVMatrix();
        });
    }
}

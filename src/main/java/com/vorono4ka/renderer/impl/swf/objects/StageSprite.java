package com.vorono4ka.renderer.impl.swf.objects;

import com.vorono4ka.editor.renderer.impl.EditorStage;

public class StageSprite extends Sprite {
    private final EditorStage stage;

    public StageSprite(EditorStage stage) {
        this.stage = stage;
    }

    public EditorStage getStage() {
        return this.stage;
    }
}

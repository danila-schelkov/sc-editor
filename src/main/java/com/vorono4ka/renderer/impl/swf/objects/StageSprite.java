package com.vorono4ka.renderer.impl.swf.objects;

import com.vorono4ka.editor.renderer.impl.Stage;

public class StageSprite extends Sprite {
    private final Stage stage;

    public StageSprite(Stage stage) {
        this.stage = stage;
    }

    public Stage getRenderer() {
        return this.stage;
    }
}

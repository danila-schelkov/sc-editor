package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;

public class StageSprite extends Sprite {
    private final Stage stage;

    public StageSprite(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }
}

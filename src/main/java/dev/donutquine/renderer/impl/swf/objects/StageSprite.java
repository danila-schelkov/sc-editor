package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.renderer.Stage;

public class StageSprite extends Sprite {
    private final Stage stage;

    public StageSprite(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }
}

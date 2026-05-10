package dev.donutquine.editor.assets;

import java.util.List;
import dev.donutquine.editor.displayObjects.SpriteSheet;

public interface TextureAsset {
    List<SpriteSheet> getSpriteSheets();

    SpriteSheet getSpriteSheet(int index);
}

package dev.donutquine.editor.assets;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.editor.renderer.impl.texture.ImageFilter;
import team.nulls.ntengine.assets.KhronosTexture;

public class KhronosTextureAssetFile extends TextureAssetFile<KhronosTexture> {
    private final Path path;

    private final SpriteSheet spriteSheet;

    KhronosTextureAssetFile(KhronosTexture asset, Path path) {
        super(asset);

        this.path = path;

        GLTexture glTexture = GLImage.createWithFormat(
            this.asset.width(), this.asset.height(), 
            true, ImageFilter.LINEAR, 
            this.asset.glFormat(), this.asset.glType(), 
            null, null, this.asset
        );

        this.spriteSheet = new SpriteSheet(0, glTexture, Collections.emptyList());
    }

    @Override
    public String getName() {
        return this.path.getFileName().toString();
    }

    @Override
    public void close() {}

    @Override
    public List<SpriteSheet> getSpriteSheets() {
        return List.of(this.spriteSheet);
    }
    
    @Override
    public SpriteSheet getSpriteSheet(int index) {
        assert index == 0 : "Khronos texture asset cannot contain more than one texture per file";
        return this.spriteSheet;
    }
}

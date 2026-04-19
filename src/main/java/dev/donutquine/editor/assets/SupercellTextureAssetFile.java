package dev.donutquine.editor.assets;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.editor.renderer.impl.texture.ImageFilter;
import dev.donutquine.editor.renderer.impl.texture.sctx.SctxPixelType;
import dev.donutquine.sctx.SctxTexture;

public class SupercellTextureAssetFile extends TextureAssetFile<SctxTexture> {
    private final Path path;

    private final SpriteSheet spriteSheet;

    SupercellTextureAssetFile(SctxTexture asset, Path path) {
        super(asset);

        this.path = path;

        GLTexture glTexture = GLImage.createWithFormat(
            this.asset.getWidth(), this.asset.getHeight(), true,
            ImageFilter.LINEAR, SctxPixelType.getFormat(this.asset.getPixelType()),
            SctxPixelType.getPixelType(this.asset.getPixelType()), 
            null, this.asset, null
        );
        this.spriteSheet = new SpriteSheet(0, glTexture, Collections.emptyList());
    }

    @Override
    public String getName() {
        return this.path.getFileName().toString();
    }

    @Override
    public void close() {
        int[] textureIds = new int[] {this.spriteSheet.getTexture().getId()};

        EditorStage stage = EditorStage.getInstance();
        stage.doInRenderThread(() -> stage.getGlContext().glDeleteTextures(textureIds.length, textureIds, 0));
        stage.reset();
    }

    @Override
    public List<SpriteSheet> getSpriteSheets() {
        return List.of(this.spriteSheet);
    }
    
    @Override
    public SpriteSheet getSpriteSheet(int index) {
        assert index == 0 : "SCTX asset cannot contain more than one texture per file";
        return this.spriteSheet;
    }
}

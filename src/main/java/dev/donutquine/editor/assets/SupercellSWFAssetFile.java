package dev.donutquine.editor.assets;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.navigation.NavigableAsset;
import dev.donutquine.editor.navigation.NavigationHistory;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.editor.renderer.impl.texture.ImageFilter;
import dev.donutquine.editor.renderer.texture.Texture;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.DisplayObjectFactory;
import dev.donutquine.sctx.SctxTexture;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.swf.textures.SWFTexture;
import team.nulls.ntengine.assets.KhronosTexture;

public class SupercellSWFAssetFile extends TextureAssetFile<SupercellSWF> implements NavigableAsset<Integer, DisplayObject>, SavableAsset {
    private final Map<Integer, DisplayObject> clonedObjects = new HashMap<>();

    private final List<SpriteSheet> spriteSheets = new ArrayList<>();

    private final NavigationHistory<Integer> navigationHistory = new NavigationHistory<>();

    SupercellSWFAssetFile(SupercellSWF asset) throws AssetLoadingException {
        super(asset);

        Path parent = asset.getPath().getParent();

        for (int i = 0; i < asset.getTextures().size(); i++) {
            SWFTexture swfTexture = asset.getTextures().get(i);
            GLTexture glTexture = createGLTexture(swfTexture, parent);
            SpriteSheet spriteSheet = new SpriteSheet(i, glTexture, getDrawBitmapsOfTexture(asset, i));
            spriteSheet.setTriangleFunction(asset.getContainerVersion() >= 5);
            this.spriteSheets.add(spriteSheet);
        }
    }

    @Override
    public String getName() {
        return this.asset.getFilename();
    }

    @Override
    public void close() {
        EditorStage stage = EditorStage.getInstance();

        int textureCount = this.spriteSheets.size();
        if (textureCount > 0) {
            int[] textureIds = new int[textureCount];
            for (int i = 0; i < textureCount; i++) {
                Texture texture = this.spriteSheets.get(i).getTexture();
                assert texture != null;

                textureIds[i] = texture.getId();
            }

            stage.doInRenderThread(() -> stage.getGlContext().glDeleteTextures(textureIds.length, textureIds, 0));
        }

        stage.reset();
    }

    @Override
    public NavigationHistory<Integer> getNavigationHistory() {
        return this.navigationHistory;
    }

    @Override
    public DisplayObject getById(Integer id) {
        return this.clonedObjects.get(id);
    }

    @Override
    public List<SpriteSheet> getSpriteSheets() {
        return this.spriteSheets;
    }
    
    @Override
    public SpriteSheet getSpriteSheet(int index) {
        return this.spriteSheets.get(index);
    }

    public DisplayObject getOrCreate(int id, String name) throws UnableToFindObjectException {
        if (this.clonedObjects.containsKey(id)) {
            return this.getById(id);
        }

        DisplayObjectOriginal displayObjectOriginal = this.asset.getOriginalDisplayObject(id, name);
        DisplayObject displayObject = DisplayObjectFactory.createFromOriginal(displayObjectOriginal, this.asset, null, this);
        this.clonedObjects.put(id, displayObject);

        return displayObject;
    }
    
    private static List<ShapeDrawBitmapCommand> getDrawBitmapsOfTexture(SupercellSWF swf, int textureIndex) {
        List<ShapeDrawBitmapCommand> bitmapCommands = new ArrayList<>();

        for (ShapeOriginal shape : swf.getShapes()) {
            for (ShapeDrawBitmapCommand command : shape.getCommands()) {
                if (command.getTextureIndex() == textureIndex) {
                    bitmapCommands.add(command);
                }
            }
        }

        return bitmapCommands;
    }

    private static GLTexture createGLTexture(SWFTexture texture, Path directory) throws AssetLoadingException {
        String textureFilename = texture.getTextureFilename();
        byte[] ktxData = texture.getKtxData();

        SctxTexture sctxTexture = null;
        KhronosTexture ktx = null;
        if (ktxData != null) {
            ktx = KhronosTextureAssetFileLoader.loadInternal(ktxData);
        } else if (textureFilename != null) {
            Path path = directory.resolve(textureFilename);

            if (textureFilename.endsWith(".zktx")) {
                ktx = CompressedKhronosTextureAssetFileLoader.loadInternal(path);
            } else if (textureFilename.endsWith(".sctx")) {
                sctxTexture = SupercellTextureAssetFileLoader.loadInternal(path);
            }
        }

        return GLImage.createWithFormat(
            texture.getWidth(), texture.getHeight(), true, 
            ImageFilter.values()[texture.getInitialTag().getTextureFilter()], 
            texture.getType().glFormat, texture.getType().glType, 
            texture.getPixels(), sctxTexture, ktx
        );
    }

    @Override
    public void save(Path path) {
        this.asset.save(path.toString(), null);
    }
}

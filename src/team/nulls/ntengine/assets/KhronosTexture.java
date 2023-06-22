package team.nulls.ntengine.assets;

import java.nio.ByteBuffer;

/**
 @author <a href="https://github.com/daniillnull">daniillnull</a>
 */
public class KhronosTexture {
    public int glType, glTypeSize, glFormat, glInternalFormat, glBaseInternalFormat;
    public int pixelWidth, pixelHeight;
    public ByteBuffer[] data;
}

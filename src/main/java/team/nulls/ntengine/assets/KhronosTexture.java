package team.nulls.ntengine.assets;

import java.nio.ByteBuffer;

/**
 @author <a href="https://github.com/daniillnull">daniillnull</a>
 */
public record KhronosTexture(int glType, int glTypeSize, int glFormat,
                             int glInternalFormat, int glBaseInternalFormat, int width,
                             int height, ByteBuffer[] levels) {
}

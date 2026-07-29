package dev.donutquine.ktx;

public record KhronosTexture(int glType, int glTypeSize, int glFormat,
                            int glInternalFormat, int glBaseInternalFormat, int width,
                            int height, byte[][] levels) {
}

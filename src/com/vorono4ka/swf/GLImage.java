package com.vorono4ka.swf;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class GLImage {
    private int textureId;

    protected int width;
    protected int height;
    protected int pixelFormat;

    public void bind() {
        GL3 gl = Stage.getInstance().getGl();
        gl.glBindTexture(GL3.GL_TEXTURE_2D, this.textureId);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public static void createWithFormat(GLImage image, boolean clampToEdge, int filter, int width, int height, Buffer pixels, int pixelFormat, int pixelType) {
        Stage stage = Stage.getInstance();
        GL3 gl = stage.getGl();

        image.setWidth(width);
        image.setHeight(height);
        image.setPixelFormat(pixelFormat);

        stage.doInRenderThread(() -> {
            if (image.getTextureId() != 0) {
                gl.glDeleteTextures(1, new int[] {image.getTextureId()}, 0);
            }

            int id = genTexture();

            image.setTextureId(id);

            int magFilter;
            int minFilter;
            switch (filter) {
//                case 1 -> {  // TODO: find out what's wrong with linear and why sprites become transparent using this filter
//                    magFilter = GL3.GL_LINEAR;
//                    minFilter = GL3.GL_LINEAR;
//                }
                case 2 -> {
                    magFilter = GL3.GL_LINEAR;
                    minFilter = GL3.GL_LINEAR_MIPMAP_NEAREST;
                }
                case 3 -> {
                    magFilter = GL3.GL_LINEAR;
                    minFilter = GL3.GL_LINEAR_MIPMAP_LINEAR;
                }
                default -> {
                    magFilter = GL3.GL_NEAREST;
                    minFilter = GL3.GL_NEAREST;
                }
            }

            int wrap = clampToEdge ? GL3.GL_CLAMP_TO_EDGE : GL3.GL_REPEAT;

            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, wrap);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, wrap);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, magFilter);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, minFilter);
    //        gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, 4);

            gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, pixelFormat, width, height, 0, pixelFormat, pixelType, pixels);
            if (gl.glGetError() == GL3.GL_INVALID_ENUM) {
                IntBuffer swizzleMask = null;
                int format = -1;

                switch (pixelFormat) {
                    case GL3.GL_LUMINANCE_ALPHA -> {
                        swizzleMask = IntBuffer.wrap(new int[] {GL3.GL_RED, GL3.GL_RED, GL3.GL_RED, GL3.GL_GREEN});
                        format = GL3.GL_RG;
                    }
                    case GL3.GL_LUMINANCE -> {
                        swizzleMask = IntBuffer.wrap(new int[] {GL3.GL_RED, GL3.GL_RED, GL3.GL_RED, 1});
                        format = GL3.GL_RED;
                    }
                    default -> {
                        assert false : "GL_INVALID_ENUM";
                    }
                }

                gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, format, width, height, 0, format, pixelType, pixels);
                if (gl.glGetError() == GL3.GL_NO_ERROR) {
                    gl.glTexParameteriv(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_SWIZZLE_RGBA, swizzleMask);
                    image.setPixelFormat(format);
                }
            }

            gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);

            gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
        });
    }

    private static int genTexture() {
        GL3 gl = Stage.getInstance().getGl();

        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, ids.get(0));
        return ids.get(0);
    }

    public static void updateSubImage(GLImage image, Buffer pixels, int xOffset, int yOffset, int width, int height, int pixelType, int mipmapLevel) {
        Stage stage = Stage.getInstance();
        GL3 gl = stage.getGl();

        stage.doInRenderThread(() -> {
            gl.glBindTexture(GL3.GL_TEXTURE_2D, image.getTextureId());
            gl.glTexSubImage2D(GL3.GL_TEXTURE_2D, mipmapLevel, xOffset, yOffset, width, height, image.getPixelFormat(), pixelType, pixels);
            gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
        });
    }
}

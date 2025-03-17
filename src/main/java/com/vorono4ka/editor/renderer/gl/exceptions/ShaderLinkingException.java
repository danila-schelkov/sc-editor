package com.vorono4ka.editor.renderer.gl.exceptions;

public class ShaderLinkingException extends RuntimeException {
    public ShaderLinkingException(String message) {
        super(message);
    }

    public ShaderLinkingException(String format, Object... parameters) {
        super(String.format(format, parameters));
    }
}

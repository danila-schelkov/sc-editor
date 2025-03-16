package com.vorono4ka.editor.renderer.exceptions;

public class ShaderCompilationException extends RuntimeException {
    public ShaderCompilationException(String message) {
        super(message);
    }

    public ShaderCompilationException(String format, Object... parameters) {
        super(String.format(format, parameters));
    }
}

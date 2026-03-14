package dev.donutquine.editor.assets.exceptions;

public class AssetLoadingException extends Exception {
    public AssetLoadingException(Throwable cause) {
        super(cause);
    }

    public AssetLoadingException(String message) {
        super(message);
    }
}

package dev.donutquine.editor.layout.cursor;

@FunctionalInterface
public interface CursorStateListener {
    void setCursor(CursorType type);
}

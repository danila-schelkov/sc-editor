package dev.donutquine.editor.gui.layout.cursor;

@FunctionalInterface
public interface CursorStateListener {
    void setCursor(CursorType type);
}

package dev.donutquine.editor.layout.cursor;

import java.awt.*;

public class Cursors {
    private Cursors() {}

    public static Cursor getCursorByType(CursorType type) {
        //noinspection MagicConstant
        return Cursor.getPredefinedCursor(type.ordinal());
    }

    public static CursorStateListener getListener(Component component) {
        return new CursorStateListener() {
            private CursorType currentCursor;

            @Override
            public void setCursor(CursorType cursorType) {
                if (currentCursor == cursorType) return;

                currentCursor = cursorType;
                component.setCursor(Cursors.getCursorByType(cursorType));
            }
        };
    }
}

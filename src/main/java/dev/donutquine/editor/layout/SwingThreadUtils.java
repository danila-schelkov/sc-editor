package dev.donutquine.editor.layout;

import javax.swing.SwingUtilities;

public final class SwingThreadUtils {
    private SwingThreadUtils() {
    }

    public static boolean isUiThread() {
        return SwingUtilities.isEventDispatchThread();
    }

    public static void runOnUiThread(Runnable runnable) {
        if (isUiThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }
}

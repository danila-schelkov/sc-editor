package dev.donutquine.editor.layout.shortcut;

import dev.donutquine.editor.SystemInfo;
import org.intellij.lang.annotations.MagicConstant;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

public class KeyboardUtils {
    @MagicConstant(flagsFromClass = java.awt.event.InputEvent.class)
    public static final int CTRL_BNT_KEY = getCtrlButton();

    @MagicConstant(flagsFromClass = java.awt.event.InputEvent.class)
    private static int getCtrlButton() {
        if (SystemInfo.IS_MAC) {
            return Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        } else {
            return InputEvent.CTRL_DOWN_MASK;
        }
    }

    @MagicConstant(flagsFromClass = java.awt.event.InputEvent.class)
    public static int ctrlButton() {
        return CTRL_BNT_KEY;
    }

    public static KeyStroke delete() {
        if (SystemInfo.IS_MAC) {
            return KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        } else {
            return KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        }
    }

    private KeyboardUtils() {}
}

package com.vorono4ka.editor.layout.shortcut;

import com.vorono4ka.editor.SystemInfo;
import org.intellij.lang.annotations.MagicConstant;

import java.awt.*;
import java.awt.event.InputEvent;

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

    private KeyboardUtils() {}
}

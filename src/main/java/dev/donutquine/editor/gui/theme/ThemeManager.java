package dev.donutquine.editor.gui.theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

public class ThemeManager {
    // TODO: load default from settings, load default from system defaults
    private static boolean isDark = false;

    public static void setTheme(boolean isDark) {
        ThemeManager.isDark = isDark;

        if (isDark) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        FlatLaf.updateUI();
    }

    public static void setThemeAnimated(boolean isDark) {
        if (ThemeManager.isDark == isDark) return;

        FlatAnimatedLafChange.showSnapshot();

        setTheme(isDark);

        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
}

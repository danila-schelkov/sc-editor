package dev.donutquine.editor.gui.theme;

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;

public class ThemeManager {
    // TODO: load default from system defaults
    private static boolean isDark = false;

    public static void setTheme(boolean isDark) {
        ThemeManager.isDark = isDark;

        if (isDark) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        UIManager.put("TabbedPane.closeArc", 999);
        UIManager.put("TabbedPane.closeCrossFilledSize", 5.5f);
        // NOTE: must be here to actually update icon colors on each theme change
        // NOTE: must be before updateUI to update icon in all existing tab components
        UIManager.put("TabbedPane.closeIcon", new FlatTabbedPaneCloseIcon());

        FlatLaf.updateUI();
    }

    public static void setThemeAnimated(boolean isDark) {
        if (ThemeManager.isDark == isDark) return;

        FlatAnimatedLafChange.showSnapshot();

        setTheme(isDark);

        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
}

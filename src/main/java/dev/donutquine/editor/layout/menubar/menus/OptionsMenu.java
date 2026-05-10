package dev.donutquine.editor.layout.menubar.menus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.settings.EditorPreferences;

public class OptionsMenu extends JMenu {
    private final EditorPreferences editorPreferences;

    private final JSlider pixelSizeSlider;
    private final JCheckBoxMenuItem wireframeModeCheckBox;
    private final JCheckBoxMenuItem exportPreserveCenterCheckBox;

    public OptionsMenu(EditorWindow window) {
        super("Options");

        this.editorPreferences = window.getEditor().getPreferences();

        // Note: opens on macos using Control+Option+O because of set mnemonic
        setMnemonic(KeyEvent.VK_O);

        JSlider pixelSizeSlider = new JSlider(0, 1000, 100);
        pixelSizeSlider.setMajorTickSpacing(250);
        pixelSizeSlider.setMinorTickSpacing(50);
        pixelSizeSlider.setPaintTicks(true);
        pixelSizeSlider.setPaintLabels(true);
        pixelSizeSlider.setSnapToTicks(true);
        pixelSizeSlider.addChangeListener(this::pixelSizeChanged);
        pixelSizeSlider.setToolTipText("Pixel size adjustment");
        this.add(pixelSizeSlider);

        this.pixelSizeSlider = pixelSizeSlider;

        JCheckBoxMenuItem wireframeModeCheckBox = new JCheckBoxMenuItem("Wireframe mode");
        wireframeModeCheckBox.setMnemonic(KeyEvent.VK_W);
        wireframeModeCheckBox.addActionListener(this::toggleWireframeMode);
        this.add(wireframeModeCheckBox);

        this.wireframeModeCheckBox = wireframeModeCheckBox;

        JCheckBoxMenuItem exportPreserveCenterCheckBox = new JCheckBoxMenuItem("Preverse stage center when export");
        exportPreserveCenterCheckBox.setState(this.editorPreferences.shouldPreserveStageCenter());
        exportPreserveCenterCheckBox.setMnemonic(KeyEvent.VK_P);
        exportPreserveCenterCheckBox.addActionListener(this::togglePreserveStageCenter);
        this.add(exportPreserveCenterCheckBox);

        this.exportPreserveCenterCheckBox = exportPreserveCenterCheckBox;
    }

    private void toggleWireframeMode(ActionEvent event) {
        EditorStage.getInstance().setWireframeEnabled(this.wireframeModeCheckBox.getState());
    }

    private void togglePreserveStageCenter(ActionEvent event) {
        editorPreferences.setShouldPreserveStageCenter(this.exportPreserveCenterCheckBox.getState());
    }

    private void pixelSizeChanged(ChangeEvent changeEvent) {
        if (pixelSizeSlider.getValue() < 1) {
            return;
        }

        this.editorPreferences.setPixelSize(getPixelSizeFactor());
    }

    /// Converts pixel size slider value from percentage to a factor
    private float getPixelSizeFactor() {
        return (float) pixelSizeSlider.getValue() / 100f;
    }
}

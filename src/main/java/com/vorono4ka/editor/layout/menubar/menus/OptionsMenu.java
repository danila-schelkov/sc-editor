package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Editor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OptionsMenu extends JMenu {
    private final Editor editor;

    private final JCheckBoxMenuItem renderPolygonsCheckBox;
    private final JSlider pixelSizeSlider;

    public OptionsMenu(Editor editor) {
        super("Options");

        this.editor = editor;

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

        JCheckBoxMenuItem renderPolygonsCheckBox = new JCheckBoxMenuItem("Render polygons");
        renderPolygonsCheckBox.setMnemonic(KeyEvent.VK_P);
        renderPolygonsCheckBox.addActionListener(this::togglePolygonRendering);
        this.add(renderPolygonsCheckBox);

        this.renderPolygonsCheckBox = renderPolygonsCheckBox;
    }

    private void togglePolygonRendering(ActionEvent event) {
        editor.setShouldDisplayPolygons(this.renderPolygonsCheckBox.getState());
    }

    private void pixelSizeChanged(ChangeEvent changeEvent) {
        if (pixelSizeSlider.getValue() < 1) {
            return;
        }

        editor.setPixelSize(getPixelSizeFactor());
    }

    /// Converts pixel size slider value from percentage to a factor
    private float getPixelSizeFactor() {
        return (float) pixelSizeSlider.getValue() / 100f;
    }
}

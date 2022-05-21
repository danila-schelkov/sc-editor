package com.vorono4ka.editor.layout.panels;

import javax.swing.*;
import java.awt.*;

public class TimelinePanel extends JPanel {
    public TimelinePanel() {
        this.setBorder(BorderFactory.createTitledBorder("Timeline"));
        this.setLayout(new GridLayout(0, 1));

        JSlider slider = new JSlider(0, 100, 5);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);

        this.add(slider);
    }
}

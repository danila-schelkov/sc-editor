package com.vorono4ka.editor.layout.panels;

import com.formdev.flatlaf.FlatLightLaf;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TimelinePanel extends JPanel {
    public static final int MIN_FRAME = 0;
    public static final int DEFAULT_START_FRAME = 0;
    public static final int DEFAULT_END_FRAME = 100;
    private final JSpinner currentFrameSpinner;
    private final JSpinner startFrameSpinner;
    private final JSpinner endFrameSpinner;
    private final JSlider slider;

    public TimelinePanel() {
        super(new BorderLayout());

        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");

        this.currentFrameSpinner = new JSpinner();
        this.startFrameSpinner = new JSpinner();
        this.endFrameSpinner = new JSpinner();
        this.slider = new JSlider(DEFAULT_START_FRAME, DEFAULT_END_FRAME, DEFAULT_START_FRAME);
        JPanel timelineControlsPanel = new JPanel(new GridLayout(1, 0, 5, 10));

//        this.playButton.setIcon(new SVGIcon("resources/play.svg"));
//        this.stopButton.setIcon(new SVGIcon("resources/stop.svg"));

        this.currentFrameSpinner.setValue(DEFAULT_START_FRAME);
        this.startFrameSpinner.setValue(DEFAULT_START_FRAME);
        this.endFrameSpinner.setValue(DEFAULT_END_FRAME);

        this.slider.setMinorTickSpacing(1);
        this.slider.setMajorTickSpacing(5);
        this.slider.setSnapToTicks(true);
        this.slider.setPaintTrack(false);
        this.slider.setPaintTicks(true);

        playButton.addActionListener(this::playButtonPressed);
        stopButton.addActionListener(this::stopButtonPressed);
        this.currentFrameSpinner.addChangeListener(this::currentFrameChanged);
        this.startFrameSpinner.addChangeListener(this::startFrameChanged);
        this.endFrameSpinner.addChangeListener(this::endFrameChanged);
        this.slider.addChangeListener(e -> {
            int value = this.slider.getValue();
            this.currentFrameSpinner.setValue(value);
        });

        timelineControlsPanel.add(playButton);
        timelineControlsPanel.add(stopButton);
        timelineControlsPanel.add(this.currentFrameSpinner);
        timelineControlsPanel.add(this.startFrameSpinner);
        timelineControlsPanel.add(this.endFrameSpinner);

        this.add(timelineControlsPanel, BorderLayout.NORTH);
        this.add(this.slider);
    }

    private void playButtonPressed(ActionEvent actionEvent) {
        Stage.getInstance().resumeAnimation();
    }

    private void stopButtonPressed(ActionEvent actionEvent) {
        Stage.getInstance().pauseAnimation();
    }

    private void currentFrameChanged(ChangeEvent e) {
        int value = validateFrame((int) this.currentFrameSpinner.getValue());
        this.currentFrameSpinner.setValue(value);

        this.slider.setValue(value);

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject == null) return;
        if (!selectedObject.isMovieClip()) return;

        MovieClip movieClip = (MovieClip) selectedObject;

        if (value == 0) {
            movieClip.resetTimelinePositionRecursive();
            Main.editor.updateCanvas();
            return;
        }

        movieClip.gotoAbsoluteTimeRecursive(value * movieClip.getMsPerFrame());
        Main.editor.updateCanvas();
    }

    private void startFrameChanged(ChangeEvent e) {
        int value = validateFrame((int) this.startFrameSpinner.getValue());
        this.startFrameSpinner.setValue(value);

        this.slider.setMinimum(value);
    }

    private void endFrameChanged(ChangeEvent e) {
        int value = validateFrame((int) this.endFrameSpinner.getValue());
        this.endFrameSpinner.setValue(value);

        this.slider.setMaximum(value);
    }

    private int validateFrame(int value) {
        return Math.max(MIN_FRAME, value);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();

        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        JPanel timelinePanel = new TimelinePanel();
//        timelinePanel.add(new JButton("WEST"), BorderLayout.WEST);
//        timelinePanel.add(new JButton("CENTER"), BorderLayout.CENTER);
//        timelinePanel.add(new JButton("EAST"), BorderLayout.EAST);
//        timelinePanel.add(new JButton("SOUTH"), BorderLayout.SOUTH);

        frame.getContentPane().add(timelinePanel);
        frame.setVisible(true);
    }
}

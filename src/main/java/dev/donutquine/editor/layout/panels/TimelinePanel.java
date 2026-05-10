package dev.donutquine.editor.layout.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class TimelinePanel extends JPanel {
    public static final int MIN_FRAME = 0;
    public static final int DEFAULT_START_FRAME = 0;
    public static final int DEFAULT_END_FRAME = 100;

    private final JSpinner currentFrameSpinner;
    private final JSpinner startFrameSpinner;
    private final JSpinner endFrameSpinner;
    private final JSlider slider;

    private MovieClip movieClip;

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

    public void setMovieClip(MovieClip movieClip) {
        this.movieClip = movieClip;

        int frameCount = movieClip.getFrameCountRecursive();
        this.endFrameSpinner.setValue(frameCount);
        this.slider.setMaximum(frameCount - 1);
    }

    private void playButtonPressed(ActionEvent actionEvent) {
        EditorStage.getInstance().resumeAnimation();
    }

    private void stopButtonPressed(ActionEvent actionEvent) {
        EditorStage.getInstance().pauseAnimation();
    }

    private void currentFrameChanged(ChangeEvent e) {
        int value = Math.max((int) this.startFrameSpinner.getValue(), Math.min((int) this.endFrameSpinner.getValue() - 1, (int) this.currentFrameSpinner.getValue()));
        this.currentFrameSpinner.setValue(value);

        this.slider.setValue(value);

        if (value == 0) {
            this.movieClip.resetTimelinePositionRecursive();
            return;
        }

        this.movieClip.gotoAbsoluteTimeRecursive(value * this.movieClip.getMsPerFrame());
    }

    private void startFrameChanged(ChangeEvent e) {
        int value = Math.max(MIN_FRAME, Math.min((int) this.startFrameSpinner.getValue(), (int) this.endFrameSpinner.getValue() - 1));
        this.startFrameSpinner.setValue(value);

        this.slider.setMinimum(value);
    }

    private void endFrameChanged(ChangeEvent e) {
        int value = Math.max(MIN_FRAME + 1, Math.max((int) this.startFrameSpinner.getValue() + 1, (int) this.endFrameSpinner.getValue()));
        this.endFrameSpinner.setValue(value);

        this.slider.setMaximum(value - 1);
    }
}

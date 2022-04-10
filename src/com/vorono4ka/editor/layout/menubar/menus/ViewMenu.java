package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;
import com.vorono4ka.editor.renderer.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewMenu extends JMenu {
    private final JCheckBoxMenuItem timelineToggle;
    private final JCheckBoxMenuItem infoToggle;

    public ViewMenu() {
        super("View");

        JMenuItem tools = new JMenu("Tools");
        JMenuItem reset = new JMenuItem("Reset");

        this.timelineToggle = new JCheckBoxMenuItem("Timeline");
        this.infoToggle = new JCheckBoxMenuItem("Info panel");
        this.infoToggle.setState(true);

        this.timelineToggle.addActionListener(this::toggleTimeline);
        this.infoToggle.addActionListener(this::toggleInfoPanel);
        reset.addActionListener(ViewMenu::resetView);

        tools.add(this.timelineToggle);
        tools.add(this.infoToggle);
        this.add(tools);
        this.add(reset);
    }

    private void toggleTimeline(ActionEvent actionEvent) {
        boolean visible = this.timelineToggle.getState();
        int height = visible ? 300 : -300;

        Window window = Main.editor.getWindow();

        JFrame frame = window.getFrame();
        Dimension minimumSize = frame.getMinimumSize();
        minimumSize.height += height;
        frame.setMinimumSize(minimumSize);

        JPanel timelinePanel = window.getTimelinePanel();
        timelinePanel.setVisible(visible);

        JSplitPane timelineSplitPane = window.getTimelineSplitPane();
        timelineSplitPane.setDividerLocation(timelineSplitPane.getHeight() - height);
    }

    private void toggleInfoPanel(ActionEvent actionEvent) {
        boolean visible = this.infoToggle.getState();
        int width = visible ? 300 : -300;

        Window window = Main.editor.getWindow();

        JFrame frame = window.getFrame();
        Dimension minimumSize = frame.getMinimumSize();
        minimumSize.width += width;
        frame.setMinimumSize(minimumSize);

        EditorInfoPanel infoPanel = window.getInfoPanel();
        infoPanel.setVisible(visible);

        JSplitPane infoSplitPane = window.getInfoSplitPane();
        int dividerLocation = Math.max(window.getCanvas().getMinimumSize().width, infoSplitPane.getWidth() - width);
        infoSplitPane.setDividerLocation(dividerLocation);
    }

    private static void resetView(ActionEvent e) {
        Stage stage = Stage.INSTANCE;

        stage.setScaleStep(39);
        stage.setScale(1);

        stage.setOffset(0, 0);

        stage.doInRenderThread(stage::updatePMVMatrix);
        Main.editor.updateCanvas();
    }
}

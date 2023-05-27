package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.EditorWindow;
import com.vorono4ka.editor.layout.panels.TimelinePanel;
import com.vorono4ka.editor.renderer.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ViewMenu extends JMenu {
    private final JCheckBoxMenuItem timelineToggle;

    public ViewMenu() {
        super("View");

        setMnemonic(KeyEvent.VK_V);

        JMenuItem tools = new JMenu("Tools");
        JMenuItem reset = new JMenuItem("Reset", KeyEvent.VK_R);

        this.timelineToggle = new JCheckBoxMenuItem("Timeline");

        this.timelineToggle.addActionListener(this::toggleTimeline);
        reset.addActionListener(ViewMenu::resetView);

        tools.add(this.timelineToggle);
        this.add(tools);
        this.add(reset);
    }

    private void toggleTimeline(ActionEvent actionEvent) {
        boolean visible = this.timelineToggle.getState();

        EditorWindow window = Main.editor.getWindow();

        JFrame frame = window.getFrame();
        Dimension minimumSize = frame.getMinimumSize();
        frame.setMinimumSize(minimumSize);

        TimelinePanel timelinePanel = window.getTimelinePanel();
        timelinePanel.setVisible(visible);

        JSplitPane timelineSplitPane = window.getTimelineSplitPane();
        timelineSplitPane.setDividerLocation(0.7f);
    }

    private static void resetView(ActionEvent e) {
        Stage stage = Stage.getInstance();

        stage.getCamera().reset();

        stage.doInRenderThread(stage::updatePMVMatrix);
        Main.editor.updateCanvas();
    }
}

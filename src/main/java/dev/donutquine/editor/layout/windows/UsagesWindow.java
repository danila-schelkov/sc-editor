package dev.donutquine.editor.layout.windows;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.panels.DisplayObjectListPanel;

public class UsagesWindow extends Window {
    private static final Dimension MINIMUM_SIZE = new Dimension(300, 0);

    private DisplayObjectListPanel displayObjectPanel;

    public UsagesWindow(String title, List<Object[]> usagesRows, SupercellSWFLayoutController controller) {
        this.frame = new JFrame(title);

        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.displayObjectPanel = new DisplayObjectListPanel(controller, usagesRows.toArray(Object[][]::new));
        this.frame.getContentPane().add(this.displayObjectPanel);
        this.frame.setMinimumSize(MINIMUM_SIZE);
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
    }
}

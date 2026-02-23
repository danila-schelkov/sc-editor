package dev.donutquine.editor.layout.shortcut;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import dev.donutquine.editor.layout.windows.EditorWindow;

public class RotationUtils {
    public static boolean rotateTrigger = false;

    public static void init(EditorWindow editorWindow) {
        JRootPane root = editorWindow.getFrame().getRootPane();

        InputMap inputMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = root.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("shift pressed SHIFT"), "rotatePressed");
        actionMap.put("rotatePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateTrigger = true;
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("released SHIFT"), "rotateReleased");
        actionMap.put("rotateReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateTrigger = false;
            }
        });
    }
}

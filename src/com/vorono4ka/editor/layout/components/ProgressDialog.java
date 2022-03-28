package com.vorono4ka.editor.layout.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProgressDialog extends JDialog {
    private final JProgressBar progressBar;

    public ProgressDialog(Component parent, String message, String title, SwingWorker worker) {
        super(parent == null ? null : SwingUtilities.getWindowAncestor(parent), title);
        setModal(true);

        ((JComponent) getContentPane()).setBorder(new EmptyBorder(8, 8, 8, 8));

        this.progressBar = new JProgressBar();

        this.progressBar.setUI(new BeautyProgressBarUI());
        this.progressBar.setIndeterminate(true);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 2, 0);
        constraints.anchor = GridBagConstraints.WEST;

        for (String line : message.split("\n")) {
            constraints.gridy++;
            add(new JLabel(line), constraints);
        }

        constraints.gridy++;
        constraints.insets = new Insets(4, 0, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(this.progressBar, constraints);

        pack();

        worker.addPropertyChangeListener(new PropertyChangeHandler());
        if (worker.getState() == SwingWorker.StateValue.PENDING) {
            worker.execute();
        }
    }

    public static void showProgress(Component parent, String message, String label, SwingWorker worker) {
        ProgressDialog dialog = new ProgressDialog(parent, message, label, worker);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public class PropertyChangeHandler implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("state")) {
                SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                if (state == SwingWorker.StateValue.DONE) {
                    dispose();
                }
            } else if (evt.getPropertyName().equals("progress")) {
                progressBar.setValue((int)evt.getNewValue());
            }
        }
    }

    private static class BeautyProgressBarUI extends BasicProgressBarUI {
        @Override
        protected void paintIndeterminate(Graphics g, JComponent c) {
            ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g.setColor(this.progressBar.getForeground());
            g.fillRoundRect(0, 0, (int) (this.progressBar.getWidth() * this.progressBar.getPercentComplete()), this.progressBar.getHeight(), 5, 5);
        }
    }
}
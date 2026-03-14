package dev.donutquine.editor.layout;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dev.donutquine.editor.assets.AssetFile;

public final class FileTab<T> extends JPanel {
    private final AssetFile<T> file;

    public FileTab(AssetFile<T> file, Runnable onSelect, Runnable onClose) {
        this.file = file;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 4));

        JLabel titleLabel = new JLabel(file.getName());
        JButton closeButton = new JButton("✕");
        closeButton.setBackground(null);

        closeButton.setFocusable(false);
        closeButton.setMargin(new Insets(0, 4, 0, 4));

        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    onSelect.run();
                } else if (e.getButton() == MouseEvent.BUTTON2) { // Close on mouse wheel click
                    onClose.run();
                }
            }
        });

        closeButton.addActionListener(e -> onClose.run());

        add(titleLabel);
        add(Box.createHorizontalStrut(6));
        add(closeButton);
    }

    public AssetFile<T> getFile() {
        return file;
    }
}

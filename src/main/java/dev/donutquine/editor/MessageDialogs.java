package dev.donutquine.editor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import dev.donutquine.renderer.impl.swf.objects.TextField;
import dev.donutquine.swf.textfields.TextFieldOriginal;

public class MessageDialogs {

    public static void showTextFieldDetailsPopup(Editor editor, TextField textField) {
        JFrame parent = editor.getWindow().getFrame();
        TextFieldOriginal original = textField.getOriginal();

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("TextField (#" + textField.getId() + ") Details");
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(10));

        // Properties section
        content.add(sectionTitle("Properties"));
        content.add(property("ID", original.getId()));
        content.add(property("Bounds", original.getBounds()));
        content.add(property("Default Text", original.getDefaultText()));
        content.add(property("Font Name", original.getFontName()));
        content.add(property("Font Size", original.getFontSize()));
        content.add(property("Outline Color", toHex(original.getOutlineColor())));
        content.add(property("Text Color", toHex(original.getColor())));
        content.add(property("Alignment", original.getAlign()));
        content.add(property("Bend Angle", original.getBendAngle()));
        content.add(property("Another Text", original.getAnotherText()));

        content.add(Box.createVerticalStrut(12));

        // Boolean section
        content.add(sectionTitle("Boolean Properties"));
        content.add(property("Auto Adjust Font Size", original.isAutoAdjustFontSize()));
        content.add(property("Bold", original.isBold()));
        content.add(property("Italic", original.isItalic()));
        content.add(property("Multiline", original.isMultiline()));
        content.add(property("Outline Enabled", original.isOutlineEnabled()));

        JOptionPane.showMessageDialog(
            parent,
            content,
            "TextField Details",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    private static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text.toUpperCase());
        label.putClientProperty(FlatClientProperties.STYLE_CLASS, "h2");
        label.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        return label;
    }

    private static JLabel property(String name, Object value) {
        return new JLabel(name + ": " + String.valueOf(value));
    }

    private static String toHex(int color) {
        return String.format("#%06X", color & 0xFFFFFF);
    }
}

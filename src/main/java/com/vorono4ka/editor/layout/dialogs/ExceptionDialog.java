package com.vorono4ka.editor.layout.dialogs;

import com.formdev.flatlaf.FlatLightLaf;
import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.components.LinkLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExceptionDialog extends JDialog {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionDialog.class);

    private static final String DIALOG_NAME = "Error";

    public static void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(ExceptionDialog::showExceptionDialog);
    }

    public static void showExceptionDialog(Thread thread, Throwable ex) {
        LOGGER.error("Exception was thrown", ex);

        ExceptionDialog dialog = new ExceptionDialog(ex);
        dialog.setVisible(true);
    }

    public ExceptionDialog(Throwable ex) {
        super((Window) null, DIALOG_NAME);

        this.getContentPane().setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.weightx = 1.0;
        c.insets = new Insets(2, 5, 5, 5);

        Map<String, String> details = new LinkedHashMap<>();
        // TODO: fetch version from manifest
        details.put("Java version", System.getProperty("java.version", "?"));
        details.put("Java VM", String.format("%s %s", System.getProperty("java.vm.vendor", "?"), System.getProperty("java.vm.name", "?")));
        details.put("Platform", String.format("%s (%s %s)", System.getProperty("os.name", "?"), System.getProperty("os.version", "?"), System.getProperty("os.arch", "?")));
        details.put("Max heap size", String.format("%d MB", Runtime.getRuntime().maxMemory() / (1024 * 1024)));

        int maxKeyLength = details.keySet().stream().map(String::length).max(Integer::compareTo).get();

        try {
            List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
            details.put("Program args", String.join(" ", args));
        } catch (Throwable t) {
            LOGGER.error("failed to get program arguments", t);
        }

        StringWriter stackTraceWriter = new StringWriter(1024);
        ex.printStackTrace(new PrintWriter(stackTraceWriter));
        String stackTrace = stackTraceWriter.toString();

        JLabel titleLabel = new JLabel("An unexpected error occurred.");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD).deriveFont(18f));

        c.gridy = 0;
        titlePanel.add(titleLabel, c);

        String url = getIssueUrl(ex, details, stackTrace);
        LinkLabel issueLink = new LinkLabel("Create a new issue at GitHub", url);
        c.gridy = 1;
        titlePanel.add(issueLink, c);

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        messageArea.setForeground(Color.BLACK);
        messageArea.setBackground(Color.WHITE);

        StringBuilder detailsTextBuilder = new StringBuilder();
        details.forEach((key, value) -> detailsTextBuilder.append(String.format("%-" + (maxKeyLength + 1) + "s: %s\n", key, value)));

        messageArea.setText(detailsTextBuilder + "\n" + stackTrace);

        JPanel buttonPanel = new JPanel();
        JButton exitButton = new JButton("Terminate Program");
        exitButton.addActionListener((event) -> System.exit(1));
        buttonPanel.add(exitButton);
        JButton closeButton = new JButton("Go back to SC Editor");
        closeButton.addActionListener((event) -> dispose());
        buttonPanel.add(closeButton);

        JScrollPane messageAreaScroller = new JScrollPane(messageArea);
        messageAreaScroller.setMinimumSize(new Dimension(600, 400));
        messageAreaScroller.setPreferredSize(new Dimension(600, 400));

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(messageAreaScroller, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();

        SwingUtilities.invokeLater(() -> messageAreaScroller.getVerticalScrollBar().setValue(0));

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - getWidth()) / 2;
        final int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        getRootPane().registerKeyboardAction((event) -> setVisible(false), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private static String getIssueUrl(Throwable throwable, Map<String, String> details, String stackTrace) {
        String issueTitle;
        try {
            issueTitle = URLEncoder.encode(throwable.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("URL encoding of title failed", e);
            issueTitle = throwable.getClass().getSimpleName();
        }

        String message = "Please describe what you did before the error occurred.\n";
        message += "**IMPORTANT!** If the error occurs with a specific file please attach it or provide link to it!\n";

        StringBuilder detailsIssueBuilder = new StringBuilder();
        details.forEach((key, value) -> detailsIssueBuilder.append(String.format("* %s: %s\n", key, value)));

        String body = String.format("%s %s\n```\n%s\n```", message, detailsIssueBuilder, stackTrace);

        String issueBody;
        try {
            issueBody = URLEncoder.encode(body, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("URL encoding of body failed", e);

            issueBody = "Please copy the displayed text in the error dialog and paste it here";
        }

        return String.format("%s/issues/new?labels=bug&title=%s&body=%s", Editor.REPO_URL, issueTitle, issueBody);
    }

    private static void throwTestException() {
        throw new RuntimeException("Inner exception message");
    }

    private static void showTestExceptionDialog() {
        try {
            throwTestException();
        } catch (Exception e) {
            showExceptionDialog(Thread.currentThread(), e);
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        showTestExceptionDialog();
    }
}


package nl.neliz.cosmiclauncher.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class OutputGUI {
    private static JFrame frame;
    private static JTextArea textArea;
    private static JButton copyButton;

    public OutputGUI() {
        initialize();
    }

    private void initialize() {
        FlatDarkLaf.setup();

        frame = new JFrame();
        frame.setTitle("Cosmic Reach game output");
        frame.setIconImage(Main.icon);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        copyButton = new JButton(LanguageManager.getTranslation("outputGui.copyButton"));
        copyButton.setFocusable(false);
        copyButton.setPreferredSize(new Dimension(600, 25));
        copyButton.addActionListener(e -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textArea.getText()), null);
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(copyButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void appendToConsole(String text) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(text + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    public static void updateLanguage() {
        if(copyButton != null) {
            copyButton.setText(LanguageManager.getTranslation("outputGui.copyButton"));

            frame.revalidate();
            frame.repaint();
        }
    }
}
package nl.neliz.cosmiclauncher.ui;

import nl.neliz.cosmiclauncher.Main;

import javax.swing.*;

public class OutputGUI {

    private JTextArea textArea;

    public OutputGUI()  {
        initialize();
    }

    private void initialize() {
        JFrame frame = new JFrame();

        frame.setTitle("Cosmic Reach game output");
        frame.setIconImage(Main.icon);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void appendToConsole(String text) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(text + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
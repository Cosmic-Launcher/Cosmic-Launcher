package nl.neliz.cosmiclauncher.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.LanguageManager;

import javax.swing.*;
import java.awt.*;

public class ProgressDialog {
    private static JDialog progressDialog;
    private static JProgressBar progressBar;

    public ProgressDialog() {
        initialize();
    }

    private void initialize() {
        FlatDarkLaf.setup();

        UIManager.put("ProgressBar.foreground", Color.decode("#32b432"));
        UIManager.put("ProgressBar.selectionBackground", Color.decode("#32b432"));

        progressDialog = new JDialog((Frame) null, LanguageManager.getTranslation("progressDialog.title"), true);
        progressDialog.setIconImage(Main.icon);
        progressDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        progressDialog.setResizable(false);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(null);
        progressDialog.setLayout(new BorderLayout());

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        progressDialog.add(progressBar, BorderLayout.CENTER);
        progressDialog.setVisible(true);
    }

    public static void updateProgress(int progress) {
        progressBar.setValue(progress);
    }

    public static void dispose() {
        progressDialog.dispose();
    }
}
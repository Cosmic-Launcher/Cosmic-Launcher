package nl.neliz.cosmiclauncher.ui.menu;

import nl.neliz.cosmiclauncher.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AboutMenu {
    public static JPanel setPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel authorLabel = new JLabel("Made by: Neliz");
        JLabel versionLabel = new JLabel("Version: 1.2");
        JLabel websiteLabel = new JLabel("Website: https://cosmic-launcher.github.io/");
        topPanel.add(authorLabel);
        topPanel.add(versionLabel);
        topPanel.add(websiteLabel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton openDirectoryButton = new JButton("Open Launcher Directory");
        openDirectoryButton.setFocusable(false);
        openDirectoryButton.addActionListener(e -> {
            openLauncherDirectory();
        });
        bottomPanel.add(openDirectoryButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);

        return panel;
    }

    private static void openLauncherDirectory() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(new File(Main.launcherDirectory));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
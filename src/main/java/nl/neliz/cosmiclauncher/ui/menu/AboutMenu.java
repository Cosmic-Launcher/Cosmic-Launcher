package nl.neliz.cosmiclauncher.ui.menu;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AboutMenu {
    private static JLabel authorLabel;
    private static JLabel versionLabel;
    private static JLabel websiteLabel;
    private static JButton openDirectoryButton;
    private static JPanel panel;

    public static JPanel setPanel() {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        authorLabel = new JLabel(LanguageManager.getTranslation("launcherGui.about.author") + "Neliz");
        versionLabel = new JLabel(LanguageManager.getTranslation("launcherGui.about.version") + "1.3");
        websiteLabel = new JLabel(LanguageManager.getTranslation("launcherGui.about.website") + "https://cosmic-launcher.github.io/");

        topPanel.add(authorLabel);
        topPanel.add(versionLabel);
        topPanel.add(websiteLabel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        openDirectoryButton = new JButton(LanguageManager.getTranslation("launcherGui.about.openDirectory"));
        openDirectoryButton.setFocusable(false);
        openDirectoryButton.addActionListener(e -> openLauncherDirectory());
        bottomPanel.add(openDirectoryButton);

        panel.add(topPanel, gbc);
        panel.add(bottomPanel, gbc);

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

    public static void updateLanguage() {
        authorLabel.setText(LanguageManager.getTranslation("launcherGui.about.author") + "Neliz");
        versionLabel.setText(LanguageManager.getTranslation("launcherGui.about.version") + "1.3");
        websiteLabel.setText(LanguageManager.getTranslation("launcherGui.about.website") + "https://cosmic-launcher.github.io/");
        openDirectoryButton.setText(LanguageManager.getTranslation("launcherGui.about.openDirectory"));

        panel.revalidate();
        panel.repaint();
    }
}
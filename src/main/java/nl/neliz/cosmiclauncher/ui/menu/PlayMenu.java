package nl.neliz.cosmiclauncher.ui.menu;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.FileUtils;
import nl.neliz.cosmiclauncher.util.LanguageManager;
import nl.neliz.cosmiclauncher.util.VersionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlayMenu {
    private static JComboBox<String> gameVersionComboBox;
    private static JButton launchButton;
    private static JPanel panel;

    public static JPanel setPanel() {
        panel = new JPanel(new BorderLayout());

        Image image = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("background.png"));
        ImageIcon imageIcon = new ImageIcon(image);

        Image scaledImage = imageIcon.getImage().getScaledInstance(-1, 400, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(imageLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        gameVersionComboBox = new JComboBox<>();
        gameVersionComboBox.setFocusable(false);
        for (VersionHandler.GameVersion version : VersionHandler.versions) {
            gameVersionComboBox.addItem(version.getVersion());
        }

        launchButton = new JButton(LanguageManager.getTranslation("launcherGui.play.launch"));
        launchButton.setFocusable(false);
        launchButton.addActionListener(e -> launch());

        bottomPanel.add(gameVersionComboBox);
        bottomPanel.add(launchButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void updateButton(String newText, boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            launchButton.setText(newText);
            launchButton.setEnabled(enabled);
        });
    }

    public static void updateLanguage() {
        launchButton.setText(LanguageManager.getTranslation("launcherGui.play.launch"));

        panel.revalidate();
        panel.repaint();
    }

    private static void launch() {
        updateButton(LanguageManager.getTranslation("launcherGui.play.preparing"), false);

        String stringGameVersion = (String) gameVersionComboBox.getSelectedItem();
        VersionHandler.GameVersion gameVersion = VersionHandler.identifyGameVersion(stringGameVersion);
        if (gameVersion == null) return;

        new Thread(() -> {
            String filePath = Main.versionsDirectory + File.separator + gameVersion.getVersion() + File.separator + "client.jar";
            if (!new File(filePath).exists() || !gameVersion.getHash().equals(FileUtils.getFileHash(filePath))) {
                FileUtils.downloadFile(gameVersion.getUrl(), filePath, true);
            }
            Main.launch(filePath);
        }).start();
    }
}
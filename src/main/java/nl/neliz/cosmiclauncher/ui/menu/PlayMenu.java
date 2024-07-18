package nl.neliz.cosmiclauncher.ui.menu;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.VersionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class PlayMenu extends JPanel {
    private static JComboBox<String> gameVersionComboBox;
    private static JButton launchButton;

    public static JPanel setPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 4, 6, 4);
        c.gridx = c.gridy = 0;

        addRow(panel, c, "Version",
                gameVersionComboBox = new JComboBox<>(),
                createSpacer()
        );

        gameVersionComboBox.setFocusable(false);
        for(VersionHandler.GameVersion version : VersionHandler.versions) {
            gameVersionComboBox.addItem(version.getVersion());
        }

        addRow(panel, c, null,
                launchButton = new JButton("Launch")
        );

        launchButton.setFocusable(false);
        launchButton.addActionListener(e -> {
            launch();
        });

        return panel;
    }

    protected static Component createSpacer() {
        return Box.createRigidArea(new Dimension(4, 0));
    }

    protected static void addRow(Container parent, GridBagConstraints c, String label, Component... components) {
        if (label != null) {
            c.gridwidth = 1;
            c.anchor = GridBagConstraints.LINE_END;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0;
            parent.add(new JLabel(label), c);
            c.gridx++;
            c.anchor = GridBagConstraints.LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
        } else {
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.NONE;
        }

        c.weightx = 1;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        for (Component comp : components) {
            panel.add(comp);
        }

        parent.add(panel, c);

        c.gridy++;
        c.gridx = 0;
    }

    public static void updateButton(String newText, boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            launchButton.setText(newText);
            launchButton.setEnabled(enabled);
        });
    }

    private static void launch() {
        updateButton("Preparing", false);

        String stringGameVersion = (String) gameVersionComboBox.getSelectedItem();
        VersionHandler.GameVersion gameVersion = VersionHandler.identifyGameVersion(stringGameVersion);
        if(gameVersion == null) return;

        new Thread(() -> {
            VersionHandler.downloadFile(gameVersion.getUrl(), nl.neliz.cosmiclauncher.Main.versionsDirectory + File.separator + gameVersion.getVersion() + File.separator + "client.jar", true);
            Main.launch(nl.neliz.cosmiclauncher.Main.versionsDirectory + File.separator + gameVersion.getVersion() + File.separator + "client.jar");
        }).start();
    }
}
package nl.neliz.cosmiclauncher.ui;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.VersionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class LauncherGUI {
    private JComboBox<String> gameVersionComboBox;
    private static JButton buttonInstall;
    private static JFrame frame;
    private JPanel panel;

    public LauncherGUI() {
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame();
        panel = this.setPanel();

        frame.setTitle("Cosmic Launcher");
        frame.setIconImage(Main.icon);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void dispose() {
        frame.dispose();
    }

    public JPanel setPanel() {
        panel = new JPanel(new GridBagLayout());
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
                buttonInstall = new JButton("Launch")
        );

        buttonInstall.setFocusable(false);
        buttonInstall.addActionListener(e -> {
            launch();
        });

        return panel;
    }

    protected static Component createSpacer() {
        return Box.createRigidArea(new Dimension(4, 0));
    }

    protected void addRow(Container parent, GridBagConstraints c, String label, Component... components) {
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
            buttonInstall.setText(newText);
            buttonInstall.setEnabled(enabled);
        });
    }

    private void launch() {
        LauncherGUI.updateButton("Preparing", false);

        String stringGameVersion = (String) gameVersionComboBox.getSelectedItem();
        VersionHandler.GameVersion gameVersion = VersionHandler.identifyGameVersion(stringGameVersion);
        if(gameVersion == null) return;

        new Thread(() -> {
            VersionHandler.downloadFile(gameVersion.getUrl(), Main.versionsDirectory + File.separator + gameVersion.getVersion() + File.separator + "client.jar");
            Main.launch(Main.versionsDirectory + File.separator + gameVersion.getVersion() + File.separator + "client.jar");
        }).start();
    }
}
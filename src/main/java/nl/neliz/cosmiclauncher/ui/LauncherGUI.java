package nl.neliz.cosmiclauncher.ui;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.util.VersionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LauncherGUI {
    private JComboBox<String> gameVersionComboBox;
    private static JButton buttonInstall;
    private JPanel panel;

    public LauncherGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initialize();
    }

    private void initialize() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame();
        JPanel panel =  this.setPanel();

        frame.setTitle("Cosmic Launcher");
        frame.setIconImage(Main.icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
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

        for(VersionHandler.GameVersion version : VersionHandler.versions) {
            gameVersionComboBox.addItem(version.getVersion());
        }

        addRow(panel, c, null,
                buttonInstall = new JButton("Launch")
        );

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
            VersionHandler.downloadClient(gameVersion.getUrl(), gameVersion.getVersion());
            Main.launch(gameVersion.getVersion());
        }).start();
    }
}

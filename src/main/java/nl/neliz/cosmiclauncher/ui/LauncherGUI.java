package nl.neliz.cosmiclauncher.ui;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.ui.menu.*;

import javax.swing.*;

public class LauncherGUI {
    private static JFrame frame;

    public LauncherGUI() {
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Cosmic Launcher");
        frame.setIconImage(Main.icon);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 125);
        frame.setLocationRelativeTo(null);

        JPanel play = PlayMenu.setPanel();
        JPanel settings = SettingsMenu.setPanel();
        JPanel about = AboutMenu.setPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        tabbedPane.addTab("Play", play);
        tabbedPane.addTab("Settings", settings);
        tabbedPane.addTab("About", about);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public static void dispose() {
        frame.dispose();
    }
}
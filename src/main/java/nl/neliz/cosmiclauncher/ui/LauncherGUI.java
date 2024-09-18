package nl.neliz.cosmiclauncher.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.ui.menu.AboutMenu;
import nl.neliz.cosmiclauncher.ui.menu.PlayMenu;
import nl.neliz.cosmiclauncher.ui.menu.SettingsMenu;
import nl.neliz.cosmiclauncher.util.LanguageManager;

import javax.swing.*;
import java.awt.*;

public class LauncherGUI {
    private static JFrame frame;
    private static JTabbedPane tabbedPane;

    public LauncherGUI() {
        initialize();
    }

    private void initialize() {
        FlatDarkLaf.setup();

        frame = new JFrame("Cosmic Launcher");
        frame.setIconImage(Main.icon);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        tabbedPane.addTab(LanguageManager.getTranslation("launcherGui.playTab"), PlayMenu.setPanel());
        tabbedPane.addTab(LanguageManager.getTranslation("launcherGui.settingsTab"), SettingsMenu.setPanel());
        tabbedPane.addTab(LanguageManager.getTranslation("launcherGui.aboutTab"), AboutMenu.setPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void updateLanguage() {
        tabbedPane.setTitleAt(0, LanguageManager.getTranslation("launcherGui.playTab"));
        tabbedPane.setTitleAt(1, LanguageManager.getTranslation("launcherGui.settingsTab"));
        tabbedPane.setTitleAt(2, LanguageManager.getTranslation("launcherGui.aboutTab"));
    }

    public static void dispose() {
        frame.dispose();
    }
}
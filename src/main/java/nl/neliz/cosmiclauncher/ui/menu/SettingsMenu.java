package nl.neliz.cosmiclauncher.ui.menu;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsMenu extends JPanel {
    public static JPanel setPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JCheckBox keepGuiOpen = new JCheckBox("Keep GUI open", Settings.keepGuiOpen);
        keepGuiOpen.setFocusable(false);
        panel.add(keepGuiOpen);
        keepGuiOpen.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "keepGuiOpen", keepGuiOpen.isSelected());
        });

        JCheckBox openGameOutput = new JCheckBox("Open game output", Settings.openGameOutput);
        openGameOutput.setFocusable(false);
        panel.add(openGameOutput);
        openGameOutput.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "openGameOutput", openGameOutput.isSelected());
        });

        JCheckBox saveLogs = new JCheckBox("Save logs", Settings.saveLogs);
        saveLogs.setFocusable(false);
        panel.add(saveLogs);
        saveLogs.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "saveLogs", saveLogs.isSelected());
        });

        JCheckBox showSnapshots = new JCheckBox("Show snapshots", Settings.showSnapshots);
        showSnapshots.setFocusable(false);
        panel.add(showSnapshots);
        showSnapshots.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "showSnapshots", showSnapshots.isSelected());
        });

        JCheckBox useLauncherDirectory = new JCheckBox("Use launcher directory", Settings.showSnapshots);
        useLauncherDirectory.setFocusable(false);
        panel.add(useLauncherDirectory);
        useLauncherDirectory.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "useLauncherDirectory", useLauncherDirectory.isSelected());
        });

        return panel;
    }
}
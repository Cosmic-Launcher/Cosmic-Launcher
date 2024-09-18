package nl.neliz.cosmiclauncher.ui.menu;

import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.Settings;
import nl.neliz.cosmiclauncher.util.LanguageManager;
import nl.neliz.cosmiclauncher.util.LanguageManager.Language;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class SettingsMenu extends JPanel {

    private static JComboBox<Language> languageDropdown;
    private static JCheckBox keepGuiOpen;
    private static JCheckBox showSnapshots;
    private static JCheckBox useLauncherDirectory;
    private static JCheckBox openGameOutput;
    private static JCheckBox saveLogs;
    private static JTextField jvmArgumentsField;
    private static JPanel mainPanel;
    private static JPanel languagePanel;
    private static JPanel generalSettingsPanel;
    private static JPanel outputSettingsPanel;
    private static JPanel jvmArgumentsPanel;

    public static JPanel setPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        languagePanel = createLanguagePanel();
        generalSettingsPanel = createGeneralSettingsPanel();
        outputSettingsPanel = createOutputSettingsPanel();
        jvmArgumentsPanel = createJvmArgumentsPanel();

        languagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, languagePanel.getPreferredSize().height));
        generalSettingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, generalSettingsPanel.getPreferredSize().height));
        outputSettingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, outputSettingsPanel.getPreferredSize().height));
        jvmArgumentsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, jvmArgumentsPanel.getPreferredSize().height));

        mainPanel.add(languagePanel);
        mainPanel.add(generalSettingsPanel);
        mainPanel.add(outputSettingsPanel);
        mainPanel.add(jvmArgumentsPanel);

        return mainPanel;
    }

    private static JPanel createLanguagePanel() {
        languagePanel = new JPanel();
        languagePanel.setLayout(new BoxLayout(languagePanel, BoxLayout.Y_AXIS));
        languagePanel.setBorder(BorderFactory.createTitledBorder(LanguageManager.getTranslation("launcherGui.settings.language")));
        languagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        LanguageManager.loadAvailableLanguages();
        List<LanguageManager.Language> languages = LanguageManager.getAvailableLanguages();

        languageDropdown = new JComboBox<>(languages.toArray(new LanguageManager.Language[0]));
        languageDropdown.setFocusable(false);

        languageDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof LanguageManager.Language) {
                    LanguageManager.Language language = (LanguageManager.Language) value;
                    setText(language.getName());
                }
                return this;
            }
        });

        for (LanguageManager.Language language : languages) {
            if (language.getCode().equals(Settings.language)) {
                languageDropdown.setSelectedItem(language);
                break;
            }
        }

        languageDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        languagePanel.add(languageDropdown);

        languageDropdown.addActionListener(e -> {
            LanguageManager.Language selectedLanguage = (LanguageManager.Language) languageDropdown.getSelectedItem();
            if (selectedLanguage != null) {
                Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "language", selectedLanguage.getCode());
                LanguageManager.loadLanguage(selectedLanguage.getCode());
                LanguageManager.updateLanguage();
            }
        });

        return languagePanel;
    }


    private static JPanel createGeneralSettingsPanel() {
        generalSettingsPanel = new JPanel();
        generalSettingsPanel.setLayout(new BoxLayout(generalSettingsPanel, BoxLayout.Y_AXIS));
        generalSettingsPanel.setBorder(BorderFactory.createTitledBorder(LanguageManager.getTranslation("launcherGui.settings.generalSettings")));
        generalSettingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        keepGuiOpen = new JCheckBox(LanguageManager.getTranslation("launcherGui.settings.keepGuiOpen"), Settings.keepGuiOpen);
        keepGuiOpen.setFocusable(false);
        keepGuiOpen.setAlignmentX(Component.LEFT_ALIGNMENT);
        generalSettingsPanel.add(keepGuiOpen);
        keepGuiOpen.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "keepGuiOpen", keepGuiOpen.isSelected());
        });

        showSnapshots = new JCheckBox(LanguageManager.getTranslation("launcherGui.settings.showSnapshots"), Settings.showSnapshots);
        showSnapshots.setFocusable(false);
        showSnapshots.setAlignmentX(Component.LEFT_ALIGNMENT);
        generalSettingsPanel.add(showSnapshots);
        showSnapshots.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "showSnapshots", showSnapshots.isSelected());
        });

        useLauncherDirectory = new JCheckBox(LanguageManager.getTranslation("launcherGui.settings.useLauncherDirectory"), Settings.useLauncherDirectory);
        useLauncherDirectory.setFocusable(false);
        useLauncherDirectory.setAlignmentX(Component.LEFT_ALIGNMENT);
        generalSettingsPanel.add(useLauncherDirectory);
        useLauncherDirectory.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "useLauncherDirectory", useLauncherDirectory.isSelected());
        });

        return generalSettingsPanel;
    }

    private static JPanel createOutputSettingsPanel() {
        outputSettingsPanel = new JPanel();
        outputSettingsPanel.setLayout(new BoxLayout(outputSettingsPanel, BoxLayout.Y_AXIS));
        outputSettingsPanel.setBorder(BorderFactory.createTitledBorder(LanguageManager.getTranslation("launcherGui.settings.outputSettings")));
        outputSettingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        openGameOutput = new JCheckBox(LanguageManager.getTranslation("launcherGui.settings.openGameOutput"), Settings.openGameOutput);
        openGameOutput.setFocusable(false);
        openGameOutput.setAlignmentX(Component.LEFT_ALIGNMENT);
        outputSettingsPanel.add(openGameOutput);
        openGameOutput.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "openGameOutput", openGameOutput.isSelected());
        });

        saveLogs = new JCheckBox(LanguageManager.getTranslation("launcherGui.settings.saveLogs"), Settings.saveLogs);
        saveLogs.setFocusable(false);
        saveLogs.setAlignmentX(Component.LEFT_ALIGNMENT);
        outputSettingsPanel.add(saveLogs);
        saveLogs.addActionListener(e -> {
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "saveLogs", saveLogs.isSelected());
        });

        return outputSettingsPanel;
    }

    private static JPanel createJvmArgumentsPanel() {
        jvmArgumentsPanel = new JPanel();
        jvmArgumentsPanel.setLayout(new BoxLayout(jvmArgumentsPanel, BoxLayout.Y_AXIS));
        jvmArgumentsPanel.setBorder(BorderFactory.createTitledBorder(LanguageManager.getTranslation("launcherGui.settings.jvmArguments")));
        jvmArgumentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        jvmArgumentsField = new JTextField(Settings.jvmArguments);
        jvmArgumentsField.setAlignmentX(Component.LEFT_ALIGNMENT);
        jvmArgumentsPanel.add(jvmArgumentsField);

        jvmArgumentsField.addActionListener(e -> {
            String newJvmArgs = jvmArgumentsField.getText();
            Settings.update(Main.launcherDirectory + File.separator + "launcherSettings.json", "jvmArguments", newJvmArgs);
            jvmArgumentsField.setFocusable(false);
            jvmArgumentsField.setFocusable(true);
        });

        return jvmArgumentsPanel;
    }

    public static void updateLanguage() {
        ((TitledBorder) languagePanel.getBorder()).setTitle(LanguageManager.getTranslation("launcherGui.settings.language"));
        ((TitledBorder) generalSettingsPanel.getBorder()).setTitle(LanguageManager.getTranslation("launcherGui.settings.generalSettings"));
        ((TitledBorder) outputSettingsPanel.getBorder()).setTitle(LanguageManager.getTranslation("launcherGui.settings.outputSettings"));
        ((TitledBorder) jvmArgumentsPanel.getBorder()).setTitle(LanguageManager.getTranslation("launcherGui.settings.jvmArguments"));

        keepGuiOpen.setText(LanguageManager.getTranslation("launcherGui.settings.keepGuiOpen"));
        showSnapshots.setText(LanguageManager.getTranslation("launcherGui.settings.showSnapshots"));
        useLauncherDirectory.setText(LanguageManager.getTranslation("launcherGui.settings.useLauncherDirectory"));
        openGameOutput.setText(LanguageManager.getTranslation("launcherGui.settings.openGameOutput"));
        saveLogs.setText(LanguageManager.getTranslation("launcherGui.settings.saveLogs"));

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}

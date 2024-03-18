package nl.neliz.cosmiclauncher;

import nl.neliz.cosmiclauncher.ui.LauncherGUI;
import nl.neliz.cosmiclauncher.ui.OutputGUI;
import nl.neliz.cosmiclauncher.util.OSHelper;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {
    public static Image icon;
    public static final String launcherDirectory = OSHelper.getBasicPath();
    public static final String versionsDirectory = launcherDirectory + File.separator + "versions";

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("icon.png"));
        new LauncherGUI();
    }

    public static void launch(String path) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", versionsDirectory + File.separator + path + File.separator + "client.jar");
            Process process = processBuilder.start();
            OutputGUI logger = new OutputGUI();

            Thread stdOutThread = new Thread(() -> {
                try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    stdInput.lines().forEach(line -> logger.appendToConsole(line));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread stdErrThread = new Thread(() -> {
                try (BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    stdError.lines().forEach(line -> logger.appendToConsole(line));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            stdOutThread.start();
            stdErrThread.start();

            LauncherGUI.updateButton("Launch", true);

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
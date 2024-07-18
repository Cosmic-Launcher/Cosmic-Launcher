package nl.neliz.cosmiclauncher;

import nl.neliz.cosmiclauncher.ui.menu.PlayMenu;
import nl.neliz.cosmiclauncher.ui.LauncherGUI;
import nl.neliz.cosmiclauncher.ui.OutputGUI;
import nl.neliz.cosmiclauncher.util.OSHelper;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static nl.neliz.cosmiclauncher.Settings.*;

public class Main {
    public static final Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("icon.png"));
    public static final String launcherDirectory = OSHelper.getBasicPath();
    public static final String versionsDirectory = launcherDirectory + File.separator + "versions";
    public static final String logsDirectory = launcherDirectory + File.separator + "logs";

    public static void main(String[] args) {
        Settings.load(launcherDirectory + File.separator + "launcherSettings.json");
        new LauncherGUI();
    }

    public static void launch(String filePath) {
        try {
            File logsDir = new File(logsDirectory);

            if (!logsDir.exists()) {
                logsDir.mkdirs();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String logFileName = dateFormat.format(new Date()) + ".log";
            File logFile = new File(logsDirectory, logFileName);

            String[] jvmArgs = jvmArguments.split("\\s+");
            String[] command = new String[jvmArgs.length + (useLauncherDirectory ? 5 : 3)];

            command[0] = "java";
            System.arraycopy(jvmArgs, 0, command, 1, jvmArgs.length);
            command[jvmArgs.length + 1] = "-jar";
            command[jvmArgs.length + 2] = filePath;
            if (useLauncherDirectory) {
                command[jvmArgs.length + 3] = "-s";
                command[jvmArgs.length + 4] = launcherDirectory;
            }

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            OutputGUI logger = null;
            if (openGameOutput) {
                logger = new OutputGUI();
            }
            FileWriter writer = null;
            if (saveLogs) {
                writer = new FileWriter(logFile);
            }

            final OutputGUI outputLogger = logger;
            final FileWriter fileWriter = writer;

            Thread stdOutThread = new Thread(() -> {
                try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = stdInput.readLine()) != null) {
                        if (outputLogger != null) {
                            outputLogger.appendToConsole(line);
                        }
                        if (fileWriter != null) {
                            fileWriter.write(line + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            stdOutThread.start();

            if (!keepGuiOpen) {
                LauncherGUI.dispose();
            }

            PlayMenu.updateButton("Launch", true);

            process.waitFor();

            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
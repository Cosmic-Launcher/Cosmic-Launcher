package nl.neliz.cosmiclauncher.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.ui.ProgressBar;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static nl.neliz.cosmiclauncher.Settings.*;

public class VersionHandler {
    public static List<GameVersion> versions = new ArrayList<>();
    public static GameVersion latest;

    static {
        loadVersionsFromFile();
    }

    public static class GameVersion {
        String version;
        String type;
        String url;

        public GameVersion(String version, String url) {
            this.version = version;
            this.url = url;
            VersionHandler.versions.add(this);
        }

        public String getVersion() {
            return version;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }
    }

    public static void loadVersionsFromFile() {
        try {
            String url = "https://github.com/n3liz/Cosmic-Launcher/raw/main/versions.json";
            String filePath = Main.versionsDirectory + File.separator + "versions.json";

            downloadFile(new URL(url), new File(filePath), false);

            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            Type listType = new TypeToken<List<GameVersion>>() {}.getType();
            List<GameVersion> allVersions = new Gson().fromJson(reader, listType);

            if (showSnapshots) {
                versions.addAll(allVersions);
            } else {
                for (GameVersion version : allVersions) {
                    if ("release".equalsIgnoreCase(version.getType())) {
                        versions.add(version);
                    }
                }
            }

            if (!versions.isEmpty()) latest = versions.get(versions.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameVersion identifyGameVersion(String version) {
        if (version.trim().isEmpty()) return null;
        for (GameVersion gameVersion : versions) {
            if (version.equalsIgnoreCase(gameVersion.getVersion())) return gameVersion;
        }
        return null;
    }

    public static void downloadFile(String url, String filePath, boolean showProgressBar) {
        try {
            downloadFile(new URL(url), new File(filePath), showProgressBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(URL url, File filePath, boolean showProgressBar) {
        try {
            URLConnection connection = url.openConnection();
            int fileSize = connection.getContentLength();

            File parentDir = filePath.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (showProgressBar) {
                SwingUtilities.invokeLater(() -> new ProgressBar());
            }

            try (InputStream inputStream = connection.getInputStream();
                 BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    if (showProgressBar) {
                        int progress = (int) ((totalBytesRead * 100) / fileSize);
                        SwingUtilities.invokeLater(() -> ProgressBar.updateProgress(progress));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (showProgressBar) {
                SwingUtilities.invokeLater(ProgressBar::dispose);
            }
        }
    }
}
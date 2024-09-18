package nl.neliz.cosmiclauncher.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.neliz.cosmiclauncher.Main;
import nl.neliz.cosmiclauncher.Settings;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        String hash;

        public GameVersion(String version, String type, String url, String hash) {
            this.version = version;
            this.type = type;
            this.url = url;
            this.hash = hash;
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

        public String getHash() {
            return hash;
        }
    }

    public static void loadVersionsFromFile() {
        try {
            String url = "https://github.com/n3liz/Cosmic-Launcher/raw/main/versions.json";
            String filePath = Main.versionsDirectory + File.separator + "versions.json";

            FileUtils.downloadFile(url, filePath, false);

            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            Type listType = new TypeToken<List<GameVersion>>() {}.getType();
            List<GameVersion> allVersions = new Gson().fromJson(reader, listType);

            if (Settings.showSnapshots) {
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
}
package nl.neliz.cosmiclauncher.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.neliz.cosmiclauncher.Main;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
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
        String url;

        public GameVersion(String version, String url) {
            this.version = version;
            this.url = url;
            VersionHandler.versions.add(this);
        }

        public String getVersion() {
            return version;
        }

        public String getUrl() {
            return url;
        }
    }

    public static void loadVersionsFromFile() {
        try {
            String filePath = Main.versionsDirectory + File.separator + "versions.json";
            File file = new File(filePath);

            downloadVersionsJson(filePath);

            FileReader reader = new FileReader(file);
            Type listType = new TypeToken<List<GameVersion>>() {}.getType();
            versions = new Gson().fromJson(reader, listType);

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

    public static void downloadVersionsJson(String path) {
        String url = "https://github.com/n3liz/Cosmic-Launcher/raw/main/versions.json";

        try {
            FileUtils.copyURLToFile(new URL(url), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadClient(String url, String path) {
        String savePath = Main.versionsDirectory + File.separator + path + File.separator + "client.jar";

        try {
            FileUtils.copyURLToFile(new URL(url), new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
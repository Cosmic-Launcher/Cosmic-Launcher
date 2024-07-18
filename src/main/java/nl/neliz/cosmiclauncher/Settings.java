package nl.neliz.cosmiclauncher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
    static JsonObject jsonObject;
    public static String jvmArguments = "-Xms512M -Xmx2G";
    public static boolean keepGuiOpen = true;
    public static boolean openGameOutput = true;
    public static boolean saveLogs = true;
    public static boolean showSnapshots = true;
    public static boolean useLauncherDirectory = true;

    public static void load(String filePath) {
        try {
            File file = new File(filePath);

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!file.exists()) {
                jsonObject = new JsonObject();
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    fileWriter.write(gson.toJson(jsonObject));
                }
            } else {
                JsonParser parser = new JsonParser();
                jsonObject = parser.parse(new FileReader(filePath)).getAsJsonObject();
            }

            if (!jsonObject.has("jvmArguments")) {
                jsonObject.addProperty("jvmArguments", jvmArguments);
            }
            if (!jsonObject.has("keepGuiOpen")) {
                jsonObject.addProperty("keepGuiOpen", keepGuiOpen);
            }
            if (!jsonObject.has("openGameOutput")) {
                jsonObject.addProperty("openGameOutput", openGameOutput);
            }
            if (!jsonObject.has("saveLogs")) {
                jsonObject.addProperty("saveLogs", saveLogs);
            }
            if (!jsonObject.has("showSnapshots")) {
                jsonObject.addProperty("showSnapshots", showSnapshots);
            }
            if (!jsonObject.has("useLauncherDirectory")) {
                jsonObject.addProperty("useLauncherDirectory", useLauncherDirectory);
            }

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(jsonObject));
            }

            jvmArguments = jsonObject.get("jvmArguments").getAsString();
            keepGuiOpen = jsonObject.get("keepGuiOpen").getAsBoolean();
            openGameOutput = jsonObject.get("openGameOutput").getAsBoolean();
            saveLogs = jsonObject.get("saveLogs").getAsBoolean();
            showSnapshots = jsonObject.get("showSnapshots").getAsBoolean();
            useLauncherDirectory = jsonObject.get("useLauncherDirectory").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(String filePath, String key, Object value) {
        try {
            if (jsonObject == null) {
                load(filePath);
            }

            if (value instanceof Boolean) {
                jsonObject.addProperty(key, (Boolean) value);
            } else if (value instanceof Number) {
                jsonObject.addProperty(key, (Number) value);
            } else {
                jsonObject.addProperty(key, value.toString());
            }

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(jsonObject));
            }

            switch (key) {
                case "jvmArguments":
                    jvmArguments = jsonObject.get("jvmArguments").getAsString();
                    break;
                case "keepGuiOpen":
                    keepGuiOpen = jsonObject.get("keepGuiOpen").getAsBoolean();
                    break;
                case "openGameOutput":
                    openGameOutput = jsonObject.get("openGameOutput").getAsBoolean();
                    break;
                case "saveLogs":
                    saveLogs = jsonObject.get("saveLogs").getAsBoolean();
                    break;
                case "showSnapshots":
                    showSnapshots = jsonObject.get("showSnapshots").getAsBoolean();
                    break;
                case "useLauncherDirectory":
                    useLauncherDirectory = jsonObject.get("useLauncherDirectory").getAsBoolean();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown setting: " + key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
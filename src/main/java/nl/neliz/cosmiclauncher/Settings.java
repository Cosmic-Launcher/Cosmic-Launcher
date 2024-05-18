package nl.neliz.cosmiclauncher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Settings {
    static JsonObject jsonObject;
    public static String jvmArguments = "-Xms512M -Xmx2G";
    public static boolean keepGuiOpen = true;
    public static boolean openGameOutput = true;
    public static boolean saveLogs = true;
    public static boolean showSnapshots = true;

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

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(jsonObject));
            }

            jvmArguments = jsonObject.get("jvmArguments").getAsString();
            keepGuiOpen = jsonObject.get("keepGuiOpen").getAsBoolean();
            openGameOutput = jsonObject.get("openGameOutput").getAsBoolean();
            saveLogs = jsonObject.get("saveLogs").getAsBoolean();
            showSnapshots = jsonObject.get("showSnapshots").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
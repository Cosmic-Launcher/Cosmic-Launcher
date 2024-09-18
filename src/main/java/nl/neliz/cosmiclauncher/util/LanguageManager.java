package nl.neliz.cosmiclauncher.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.neliz.cosmiclauncher.ui.LauncherGUI;
import nl.neliz.cosmiclauncher.ui.OutputGUI;
import nl.neliz.cosmiclauncher.ui.menu.AboutMenu;
import nl.neliz.cosmiclauncher.ui.menu.PlayMenu;
import nl.neliz.cosmiclauncher.ui.menu.SettingsMenu;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager {
    private static final List<Language> languages = new ArrayList<>();
    private static final Map<String, String> translations = new HashMap<>();

    static {
        loadAvailableLanguages();
    }

    public static class Language {
        String code;
        String name;

        public Language(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static void loadAvailableLanguages() {
        languages.clear();
        String[] languageCodes = {"en_us", "nl_nl"};

        for (String languageCode : languageCodes) {
            String languageName = getLanguageName(languageCode);
            languages.add(new Language(languageCode, languageName));
        }
    }

    public static void loadLanguage(String languageCode) {
        translations.clear();
        String resourcePath = "lang/" + languageCode + ".json";
        try (InputStreamReader reader = new InputStreamReader(
                LanguageManager.class.getClassLoader().getResourceAsStream(resourcePath))) {
            if (reader == null) {
                throw new IOException("Language file not found: " + resourcePath);
            }
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            parseTranslations(jsonObject, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseTranslations(JsonObject jsonObject, String prefix) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive()) {
                translations.put(key, value.getAsString());
            } else if (value.isJsonObject()) {
                parseTranslations(value.getAsJsonObject(), key);
            }
        }
    }

    public static List<Language> getAvailableLanguages() {
        return new ArrayList<>(languages);
    }

    private static String getLanguageName(String languageCode) {
        String resourcePath = "lang/" + languageCode + ".json";
        try (InputStreamReader reader = new InputStreamReader(
                LanguageManager.class.getClassLoader().getResourceAsStream(resourcePath))) {
            if (reader == null) {
                throw new IOException("Language file not found: " + resourcePath);
            }
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.getAsJsonObject("metadata").get("name").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            return languageCode;
        }
    }

    public static String getTranslation(String key) {
        return translations.getOrDefault(key, key);
    }

    public static void updateLanguage() {
        LauncherGUI.updateLanguage();
        PlayMenu.updateLanguage();
        SettingsMenu.updateLanguage();
        AboutMenu.updateLanguage();
        OutputGUI.updateLanguage();
    }
}
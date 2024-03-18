package nl.neliz.cosmiclauncher.util;

import java.io.File;

public class OSHelper {

    public static String getBasicPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String basicPath;
        if (osName.contains("win")) {
            basicPath = System.getenv("APPDATA") + File.separator + "cosmic-launcher";
        } else if (osName.contains("mac")) {
            basicPath = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "cosmic-launcher";
        } else {
            basicPath = System.getProperty("user.home") + File.separator + "cosmic-launcher";
        }
        return basicPath;
    }
}
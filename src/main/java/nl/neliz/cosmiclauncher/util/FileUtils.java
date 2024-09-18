package nl.neliz.cosmiclauncher.util;

import nl.neliz.cosmiclauncher.ui.ProgressDialog;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {
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
                SwingUtilities.invokeLater(() -> new ProgressDialog());
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
                        SwingUtilities.invokeLater(() -> ProgressDialog.updateProgress(progress));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (showProgressBar) {
                SwingUtilities.invokeLater(ProgressDialog::dispose);
            }
        }
    }

    public static String getFileHash(String filePath) {
        return getFileHash(new File(filePath));
    }

    public static String getFileHash(File filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] fileBytes = Files.readAllBytes(filePath.toPath());
            byte[] hashBytes = digest.digest(fileBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

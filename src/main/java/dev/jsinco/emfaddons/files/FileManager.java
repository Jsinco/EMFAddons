package dev.jsinco.emfaddons.files;

import dev.jsinco.emfaddons.EMFAddons;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileManager {
    private final static EMFAddons plugin = EMFAddons.getInstance();
    private final static File dataFolder = new File(plugin.getDataFolder().getParent() + File.separator + "EvenMoreFish" + File.separator + "EMFAddons");

    private final String fileName;
    private final File file;
    private YamlConfiguration yamlConfiguration;

    public FileManager(String fileName) {
        //Bukkit.broadcastMessage(dataFolder.getAbsolutePath());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.fileName = fileName;
        this.file = new File(dataFolder, fileName);
    }



    public void generateFile() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                InputStream inputStream = plugin.getResource(fileName);
                if (inputStream != null) {
                    OutputStream outputStream = Files.newOutputStream(file.toPath());
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getYamlFile() {
        if (yamlConfiguration == null) {
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        }
        return yamlConfiguration;
    }

    public YamlConfiguration generateYamlFile() {
        generateFile();
        return getYamlFile();
    }

    public File generateAndGetFile() {
        generateFile();
        return file;
    }

    public File getFile() {
        return file;
    }

    public static File getDataFolder() {
        return dataFolder;
    }
}

package me.enchantbook.skyblockworldedit.Configurations;

import com.google.common.base.Charsets;
import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManager {

    private File file;

    private String name, directory;

    private YamlConfiguration configuration;

    public FileManager(JavaPlugin plugin, String name, String directory) {
        setName(name);
        setDirectory(directory);

        file = new File(directory, name + ".yml");

        if(!file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(getFile());
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(getFile());

        InputStream defConfigStream = SkyblockWorldEdit.getInstance().getResource(this.name + ".yml");
        if(defConfigStream == null) {
            return;
        }

        this.configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }

    public void setFile(File file) {
        this.file = file;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public String getDirectory() {
        return this.directory;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }
}

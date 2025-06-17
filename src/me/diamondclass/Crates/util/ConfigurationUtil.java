package me.diamondclass.Crates.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationUtil {
  private final Plugin plugin;
  private final Logger logger;

  public ConfigurationUtil(Plugin plugin) {
    this.plugin = plugin;
    this.logger = plugin.getLogger();
  }

  public void createConfiguration(String filePath) {
    File configFile = new File(replaceDataFolder(filePath));
    String fileName = configFile.getName();

    if (configFile.exists()) {
      logger.info("Skipped '" + fileName + "' creation because it already exists!");
      return;
    }

    try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream(fileName)) {
      File parent = configFile.getParentFile();
      if (parent != null) parent.mkdirs();

      if (in != null) {
        Files.copy(in, configFile.toPath());
        logger.info("File '" + fileName + "' has been created!");
      } else {
        configFile.createNewFile();
        logger.info("Empty file '" + fileName + "' has been created!");
      }
    } catch (IOException e) {
      logger.severe("An exception was caught while creating '" + fileName + "': " + e.getMessage());
    }
  }

  public YamlConfiguration getConfiguration(String filePath) {
    File file = new File(replaceDataFolder(filePath));
    if (file.exists()) {
      return YamlConfiguration.loadConfiguration(file);
    } else {
      return new YamlConfiguration();
    }
  }

  public void saveConfiguration(YamlConfiguration yaml, String filePath, boolean async) {
    if (async) {
      plugin.getServer().getScheduler().runTaskAsynchronously(plugin,
              () -> saveSync(yaml, filePath));
    } else {
      saveSync(yaml, filePath);
    }
  }

  private void saveSync(YamlConfiguration yaml, String filePath) {
    try {
      yaml.save(replaceDataFolder(filePath));
    } catch (IOException e) {
      logger.severe("Unable to save configuration file '" + filePath + "': " + e.getMessage());
    }
  }

  public void deleteConfiguration(String filePath, boolean async) {
    if (async) {
      plugin.getServer().getScheduler().runTaskAsynchronously(plugin,
              () -> deleteSync(filePath));
    } else {
      deleteSync(filePath);
    }
  }

  private void deleteSync(String filePath) {
    File file = new File(replaceDataFolder(filePath));
    if (file.exists() && !file.delete()) {
      logger.warning("Could not delete configuration file '" + filePath + "'");
    }
  }

  public YamlConfiguration reloadConfiguration(String filePath) {
    try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream(new File(filePath).getName())) {
      File configFile = new File(replaceDataFolder(filePath));
      if (in != null && configFile.exists()) {
      }
    } catch (IOException ignored) {}

    return getConfiguration(filePath);
  }

  private String replaceDataFolder(String filePath) {
    return filePath.replace("%datafolder%", plugin.getDataFolder().toPath().toString());
  }
}

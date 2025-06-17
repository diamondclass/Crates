package me.diamondclass.Crates;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.diamondclass.Crates.command.CratesCommandExecutor;
import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.CrateManager;
import me.diamondclass.Crates.listeners.ListenerInitializer;
import me.diamondclass.Crates.player.CratesPlayerManager;
import me.diamondclass.Crates.util.CommandRegister;
import me.diamondclass.Crates.util.ConfigurationUtil;

public class Crates extends JavaPlugin {
  private CrateManager crateManager;
  private CratesPlayerManager cratesPlayerManager;
  private ListenerInitializer listenerInitializer;
  private ConfigurationUtil configurationUtil;
  private CratesConfig cratesConfig;

  @Override
  public void onEnable() {
    configurationUtil = new ConfigurationUtil(this);
    configurationUtil.createConfiguration("%datafolder%/settings.yml");
    configurationUtil.createConfiguration("%datafolder%/language.yml");

    reloadConfigs();

    crateManager = new CrateManager(configurationUtil, cratesConfig, this);
    cratesPlayerManager = new CratesPlayerManager(crateManager, configurationUtil, getServer());

    String mainCommand = cratesConfig.getRawPluginMainCommand();
    boolean registered = CommandRegister.registerCommand(
            mainCommand,
            new CratesCommandExecutor(crateManager, cratesPlayerManager, cratesConfig, this),
            this
    );

    crateManager.loadCrates();
    crateManager.spawnHolograms();
    listenerInitializer = new ListenerInitializer((Plugin) this, crateManager, cratesPlayerManager, cratesConfig);
    listenerInitializer.initialize();

    Server server = getServer();
    server.getConsoleSender().sendMessage("§3Ulises §7| §fPlugin §asuccessfully §floaded");
    server.getConsoleSender().sendMessage("§e-------------------------------------------");
    server.getConsoleSender().sendMessage("§3Ulises §7- §bCrates system");
    server.getConsoleSender().sendMessage("§7§m-------------------------------------------");
    server.getConsoleSender().sendMessage("§fVersion: §a" + getDescription().getVersion());
    server.getConsoleSender().sendMessage("§fAuthor: §b" + getDescription().getAuthors());
    server.getConsoleSender().sendMessage("§fYour main command is §b" + mainCommand);
    server.getConsoleSender().sendMessage("§e-------------------------------------------");
  }

  public void reloadConfigs() {
    configurationUtil.reloadConfiguration("%datafolder%/settings.yml");
    configurationUtil.reloadConfiguration("%datafolder%/language.yml");

    FileConfiguration settingsConfig = configurationUtil.getConfiguration("%datafolder%/settings.yml");
    FileConfiguration langConfig = configurationUtil.getConfiguration("%datafolder%/language.yml");

    if (cratesConfig == null) {
      cratesConfig = new CratesConfig(settingsConfig, langConfig);
    } else {
      cratesConfig.reload(settingsConfig, langConfig);
    }

    if (crateManager != null) {
      crateManager.despawnHolograms();
      crateManager.loadCrates();
      crateManager.spawnHolograms();
    }

    if (listenerInitializer != null) {
      listenerInitializer.deinitialize();
      listenerInitializer.initialize();
    }
  }

  @Override
  public void onDisable() {
    for (Player player : getServer().getOnlinePlayers()) {
      cratesPlayerManager.savePlayer(player.getUniqueId(), false);
    }
    if (crateManager != null) {
      crateManager.saveCrates(false);
      crateManager.despawnHolograms();
    }
    if (listenerInitializer != null) {
      listenerInitializer.deinitialize();
    }
    getServer().getConsoleSender().sendMessage("§3Ulises §7| §fPlugin §csuccessfully §fdisabled");
  }

  public CratesConfig getCratesConfig() {
    return cratesConfig;
  }

  public ConfigurationUtil getConfigurationUtil() {
    return configurationUtil;
  }

  public CrateManager getCrateManager() {
    return crateManager;
  }
}

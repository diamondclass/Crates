package me.diamondclass.Crates;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

  @Override
  public void onEnable() {
    ConfigurationUtil configurationUtil = new ConfigurationUtil(this);
    configurationUtil.createConfiguration("%datafolder%/settings.yml");
    configurationUtil.createConfiguration("%datafolder%/language.yml");

    FileConfiguration settingsConfig = configurationUtil.getConfiguration("%datafolder%/settings.yml");
    FileConfiguration langConfig = configurationUtil.getConfiguration("%datafolder%/language.yml");

    CratesConfig cratesConfig = new CratesConfig(settingsConfig, langConfig);
    crateManager = new CrateManager(configurationUtil, cratesConfig, this);
    cratesPlayerManager = new CratesPlayerManager(crateManager, configurationUtil, getServer());

    String mainCommand = cratesConfig.getRawPluginMainCommand();
    boolean registered = CommandRegister.registerCommand(
            mainCommand,
            new CratesCommandExecutor(crateManager, cratesPlayerManager, cratesConfig, this),
            this
    );

    if (!registered) {
      getLogger().severe("An error has ocurred with your command: '" + mainCommand + "'");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    crateManager.loadCrates();
    crateManager.spawnHolograms();
    listenerInitializer = new ListenerInitializer(this, crateManager, cratesPlayerManager, cratesConfig);
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
}

package me.diamondclass.Crates.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.diamondclass.Crates.Crates;
import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.util.ConfigurationUtil;

public class ReloadCommand implements CommandExecutor {
    private final Crates plugin;
    private final ConfigurationUtil configurationUtil;
    private final CratesConfig cratesConfig;

    public ReloadCommand(Crates plugin, ConfigurationUtil configurationUtil, CratesConfig cratesConfig) {
        this.plugin = plugin;
        this.configurationUtil = configurationUtil;
        this.cratesConfig = cratesConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("crates.reload")) {
            sender.sendMessage(cratesConfig.getNoPermission());
            return true;
        }

        configurationUtil.reloadConfiguration("settings.yml");
        configurationUtil.reloadConfiguration("language.yml");

        cratesConfig.reload(
                configurationUtil.getConfiguration("settings.yml"),
                configurationUtil.getConfiguration("language.yml")
        );

        plugin.getCrateManager().loadCrates();
        plugin.getCrateManager().despawnHolograms();
        plugin.getCrateManager().spawnHolograms();

        sender.sendMessage(cratesConfig.getReloadSuccess());
        return true;
    }
}

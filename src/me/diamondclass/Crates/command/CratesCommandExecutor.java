package me.diamondclass.Crates.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.CrateManager;
import me.diamondclass.Crates.player.CratesPlayerManager;

public class CratesCommandExecutor implements CommandExecutor {
  private final Map<String, CratesCommand> cratesCommands = new HashMap<>();
  private final CratesConfig cratesConfig;
  private final CratesCommand helpCommand;

  private void addCommand(final CratesCommand cratesCommand) {
    this.cratesCommands.put(cratesCommand.getName(), cratesCommand);
  }

  public CratesCommandExecutor(final CrateManager crateManager, final CratesPlayerManager cratesPlayerManager,
      final CratesConfig cratesConfig, final Plugin plugin) {
    final Server server = plugin.getServer();

    this.cratesConfig = cratesConfig;
    this.helpCommand = new HelpCommand(plugin, cratesConfig, cratesCommands);

    addCommand(new setlocCommand(crateManager, cratesConfig));
    addCommand(new CheckCommand(cratesPlayerManager, cratesConfig));
    addCommand(new ClaimCommand(cratesPlayerManager, cratesConfig));
    addCommand(new ContentsCommand(crateManager, cratesConfig));
    addCommand(new CreateCommand(crateManager, cratesConfig));
    addCommand(new DisplaynameCommand(crateManager, cratesConfig));
    addCommand(new RemoveCommand(crateManager, cratesConfig));
    addCommand(new KeyAllCommand(crateManager, cratesPlayerManager, cratesConfig, server));
    addCommand(new KeyCommand(cratesPlayerManager, crateManager, cratesConfig, server));
    addCommand(new ListCommand(crateManager, cratesConfig));
    addCommand(new RemoveLocationCommand(crateManager, cratesConfig));
    addCommand(new RowsCommand(crateManager, cratesConfig));
  }

  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    if (args.length > 0) {
      final String subCommand = args[0].toLowerCase();

      if (this.cratesCommands.containsKey(subCommand)) {
        final CratesCommand cratesCommand = this.cratesCommands.get(subCommand);

        if (cratesCommand.requirePlayer() && !(sender instanceof Player)) {
          sender.sendMessage(cratesConfig.getNoConsole());
        } else if (cratesCommand.requireAdmin() && !sender.hasPermission("ulises.admin")) {
          sender.sendMessage(cratesConfig.getNoPermission());
        } else if (args.length < cratesCommand.getArgCount()) {
          sender.sendMessage(cratesConfig.getCommandUsage(label, cratesCommand.getName(), cratesCommand.getArgs()));
        } else {
          cratesCommand.execute(sender, label, args);
        }
      } else {
        helpCommand.execute(sender, label, args);
      }
    } else {
      helpCommand.execute(sender, label, args);
    }

    return true;
  }
}

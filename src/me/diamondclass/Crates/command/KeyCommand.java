package me.diamondclass.Crates.command;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.Crate;
import me.diamondclass.Crates.crate.CrateManager;
import me.diamondclass.Crates.player.CratesPlayer;
import me.diamondclass.Crates.player.CratesPlayerManager;

class KeyCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;
  private final Server server;

  KeyCommand(CratesPlayerManager cratesPlayerManager, CrateManager crateManager, CratesConfig cratesConfig,
      Server server) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
    this.server = server;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    try {
      String playerName = args[1];
      String keyName = args[2];
      int amount = Integer.parseInt(args[3]);
      Crate crate = this.crateManager.getCrate(keyName);
      if (crate == null) {
        sender.sendMessage(cratesConfig.getNoCrate());
      } else {
        Player player = this.server.getPlayer(playerName);
        if (player != null && player.isOnline()) {
          CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(player.getUniqueId());

          cratesPlayer.giveKeys(crate, amount);
          player.sendMessage(cratesConfig.getReceivedKeys(sender.getName(), amount, crate.getDisplayName()));
          sender.sendMessage(cratesConfig.getKeySuccess(amount, crate.getDisplayName(), playerName));
        }
      }
    } catch (NumberFormatException exception) {
      sender.sendMessage(cratesConfig.getInvalidNumber());
    }
  }

  @Override
  public String getName() {
    return "key";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getKeyDescription();
  }

  @Override
  public String getArgs() {
    return "<player> <crate> <amount>";
  }

  @Override
  public boolean requireAdmin() {
    return true;
  }

  @Override
  public boolean requirePlayer() {
    return false;
  }

  @Override
  public int getArgCount() {
    return 4;
  }
}

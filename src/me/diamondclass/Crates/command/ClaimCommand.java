package me.diamondclass.Crates.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.player.CratesPlayer;
import me.diamondclass.Crates.player.CratesPlayerManager;

class ClaimCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  private final CratesConfig cratesConfig;

  ClaimCommand(final CratesPlayerManager cratesPlayerManager, final CratesConfig cratesConfig) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    final CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(((Entity) sender).getUniqueId());

    if (cratesPlayer.getPendingKeys().isEmpty()) {
      sender.sendMessage(cratesConfig.getNoKeysPending());
    } else {
      final int result = cratesPlayer.claimKeys();

      if (result > 0) {
        sender.sendMessage(cratesConfig.getClaimSuccess(result));
      } else {
        sender.sendMessage(cratesConfig.getNoSpace());
      }
    }
  }

  @Override
  public String getName() {
    return "claim";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getClaimDescription();
  }

  @Override
  public String getArgs() {
    return "";
  }

  @Override
  public boolean requireAdmin() {
    return false;
  }

  @Override
  public boolean requirePlayer() {
    return true;
  }

  @Override
  public int getArgCount() {
    return 0;
  }
}

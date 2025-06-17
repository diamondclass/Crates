package me.diamondclass.Crates.command;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.Crate;
import me.diamondclass.Crates.player.CratesPlayer;
import me.diamondclass.Crates.player.CratesPlayerManager;

class CheckCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  private final CratesConfig cratesConfig;

  CheckCommand(final CratesPlayerManager cratesPlayerManager, final CratesConfig cratesConfig) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(final CommandSender sender, final String label, final String[] args) {
    final CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(((Entity) sender).getUniqueId());
    final Map<Crate, Integer> pendingKeys = cratesPlayer.getPendingKeys();
    if (pendingKeys.isEmpty()) {
      sender.sendMessage(cratesConfig.getNoKeysPending());
    } else {
      int amount = 0;

      for (final Iterator<Integer> iterator = pendingKeys.values().iterator(); iterator.hasNext();) {
        final int amount1 = ((Integer) iterator.next()).intValue();
        amount += amount1;
      }

      sender.sendMessage(cratesConfig.getCheckSuccess(amount));
    }
  }

  @Override
  public String getName() {
    return "check";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getCheckDescription();
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

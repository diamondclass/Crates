package me.diamondclass.Crates.command;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.Crate;
import me.diamondclass.Crates.crate.CrateManager;

class setlocCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  setlocCommand(final CrateManager crateManager, final CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(final CommandSender sender, final String label, final String[] args) {
    final Block block = ((Player) sender).getTargetBlock(null, 10);
    if (block == null) {
      sender.sendMessage(cratesConfig.getNoBlock());
    } else {
      final String crateName = args[1];
      final Crate crate = this.crateManager.getCrate(crateName);
      final Location blockLocation = block.getLocation().add(new Vector(0.5f, 0.0f, 0.5f));

      if (crate == null) {
        sender.sendMessage(cratesConfig.getNoCrate());
      } else {

        for (Crate aCrate : crateManager.getCrates()) {
          if (aCrate.checkLocation(blockLocation)) {
            sender.sendMessage(cratesConfig.getAddLocationAlreadySet());
            return;
          }
        }

        crate.addLocation(blockLocation);
        sender.sendMessage(cratesConfig.getAddLocationSuccess(crateName));
      }
    }
  }

  @Override
  public String getName() {
    return "setloc";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getAddLocationDescription();
  }

  @Override
  public String getArgs() {
    return "<crate>";
  }

  @Override
  public boolean requireAdmin() {
    return true;
  }

  @Override
  public boolean requirePlayer() {
    return true;
  }

  @Override
  public int getArgCount() {
    return 2;
  }
}

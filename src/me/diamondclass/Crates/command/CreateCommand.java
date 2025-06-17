package me.diamondclass.Crates.command;

import org.bukkit.command.CommandSender;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.CrateManager;

class CreateCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  CreateCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    String crateName = args[1];
    this.crateManager.loadCrate(crateName);
    sender.sendMessage(cratesConfig.getCreateSuccess(crateName));
  }

  @Override
  public String getName() {
    return "create";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getCreateDescription();
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
    return false;
  }

  @Override
  public int getArgCount() {
    return 2;
  }
}

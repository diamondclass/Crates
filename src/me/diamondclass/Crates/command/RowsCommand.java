package me.diamondclass.Crates.command;

import org.bukkit.command.CommandSender;

import me.diamondclass.Crates.config.CratesConfig;
import me.diamondclass.Crates.crate.Crate;
import me.diamondclass.Crates.crate.CrateManager;
import me.diamondclass.Crates.util.InventoryUtil;

class RowsCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  RowsCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    String crateName = args[1];
    final Crate crate = crateManager.getCrate(args[1]);

    if (crate != null) {
      final int rows = InventoryUtil.getValidRow(args[2]);

      crate.setRows(rows);
      sender.sendMessage(cratesConfig.getRowsSuccess(crateName, rows));
    } else {
      sender.sendMessage(cratesConfig.getNoCrate());
    }
  }

  @Override
  public String getName() {
    return "rows";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getRowsDescription();
  }

  @Override
  public String getArgs() {
    return "<crate> <rows>";
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
    return 3;
  }
}

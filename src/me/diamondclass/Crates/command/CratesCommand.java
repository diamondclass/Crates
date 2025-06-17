package me.diamondclass.Crates.command;

import org.bukkit.command.CommandSender;

public interface CratesCommand {
  void execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);

  String getName();

  String getDescription();

  String getArgs();

  int getArgCount();

  boolean requireAdmin();

  boolean requirePlayer();
}

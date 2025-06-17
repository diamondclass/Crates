package me.diamondclass.Crates.util;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import me.diamondclass.Crates.command.CratesCommandExecutor;

public class CommandRegister {
    public static boolean registerCommand(String name, CratesCommandExecutor executor, Plugin plugin) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            PluginCommand pluginCommand = plugin.getServer().getPluginCommand(name);
            if (pluginCommand != null) {
                pluginCommand.setExecutor(executor);
                return true;
            }
            Command dynamicCommand = new Command(name) {
                @Override
                public boolean execute(org.bukkit.command.CommandSender sender, String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };
            commandMap.register(plugin.getDescription().getName(), dynamicCommand);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

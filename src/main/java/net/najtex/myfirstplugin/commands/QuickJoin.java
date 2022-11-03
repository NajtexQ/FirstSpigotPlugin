package net.najtex.myfirstplugin.commands;

import net.najtex.myfirstplugin.MyFirstPlugin;
import net.najtex.myfirstplugin.minigame.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuickJoin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /quickjoin <gameName>");
            return true;
        }

        MyFirstPlugin.arenaManager.QuickJoin((Player) sender, args[0]);

        return true;
    }
}

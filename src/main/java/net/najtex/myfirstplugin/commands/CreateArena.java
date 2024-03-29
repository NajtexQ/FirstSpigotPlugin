package net.najtex.myfirstplugin.commands;

import net.najtex.myfirstplugin.MyFirstPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if the player has permission to use this command
        if (!sender.hasPermission("myfirstplugin.createarena")) {
            sender.sendMessage("You do not have permission to use this command!");
            return true;
        }

        // Check if the player has provided the correct number of arguments
        if (args.length != 3) {
            sender.sendMessage("Usage: /createarena <gameName> <gameMode> <numOfTeams>");
            return true;
        }

        // Create the arena
        MyFirstPlugin.arenaManager.createArena(args[0], args[1], Integer.parseInt(args[2]), false);

        sender.sendMessage("Arena created!");

        return true;
    }

    private boolean isInteger(String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

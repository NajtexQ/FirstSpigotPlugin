package net.najtex.myfirstplugin.commands;

import net.najtex.myfirstplugin.minigame.ArenaManager;
import net.najtex.myfirstplugin.minigame.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndGame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("myfirstplugin.endgame")) {
                if (args.length == 1) {
                    if (ArenaManager.getArenaByName(args[0]) != null) {
                        ArenaManager.getArenaByName(args[0]).changeState(GameState.END);
                    } else {
                        player.sendMessage("Arena " + args[0] + " does not exist!");
                    }
                } else {
                    player.sendMessage("Usage: /endgame <arenaName>");
                }
            } else {
                player.sendMessage("You do not have permission to use this command!");
            }
        } else {
            sender.sendMessage("You must be a player to use this command!");
        }
        return true;
    }
}
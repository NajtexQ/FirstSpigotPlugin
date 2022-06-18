package net.najtex.myfirstplugin.commands;

import net.najtex.myfirstplugin.world.WorldManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteWorld implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            String worldName = args[0];

            Player player = (Player) sender;
            player.sendMessage("Deleting world...");

            WorldManager.deleteWorld(worldName);
            player.sendMessage("World deleted!");
        }
        return true;
    }

}

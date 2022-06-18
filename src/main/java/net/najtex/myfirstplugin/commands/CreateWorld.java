package net.najtex.myfirstplugin.commands;

import net.najtex.myfirstplugin.world.WorldManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateWorld implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            String worldName = args[0];

            Player player = (Player) sender;
            player.sendMessage("Creating world...");

            WorldManager.createWorld(worldName);
            player.sendMessage("World created!");
        }
        return true;
    }

}

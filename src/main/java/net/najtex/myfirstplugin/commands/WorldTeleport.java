package net.najtex.myfirstplugin.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getWorld;

public class WorldTeleport implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            String worldName = args[0];

            Player player = (Player) sender;

            World world = getWorld(worldName);

            player.teleport(world.getSpawnLocation());

        }
        return true;
    }
}

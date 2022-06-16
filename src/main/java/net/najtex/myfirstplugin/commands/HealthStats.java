package net.najtex.myfirstplugin.commands;

import net.najtex.myfirstplugin.MyFirstPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class HealthStats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            player.sendMessage("Your health is " + player.getHealth());

            try {
                MyFirstPlugin.socketConnection.sendMessage(player.getName() + " has " + player.getHealth() + " health!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
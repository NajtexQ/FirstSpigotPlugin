package net.najtex.myfirstplugin.listeners;

import net.najtex.myfirstplugin.MyFirstPlugin;
import net.najtex.myfirstplugin.SocketConnection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.IOException;
import java.net.SocketException;

import static org.bukkit.Bukkit.getLogger;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("You have moved!" + player.getName());

        if (player.getName().equals("Najtex")) {

            // Get player coordinates
            double x = event.getTo().getX();
            double y = event.getTo().getY();
            double z = event.getTo().getZ();

            // Send coordinates to server
            try {
                getLogger().info("Sending coordinates to server...");
                MyFirstPlugin.socketConnection.sendMessage("x=" + x + " &y=" + y + " &z=" + z);
            } catch (IOException e) {
                getLogger().info("Error sending coordinates to server!");
            }
        }
    }
}

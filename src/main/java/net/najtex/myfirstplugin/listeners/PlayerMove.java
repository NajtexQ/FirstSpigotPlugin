package net.najtex.myfirstplugin.listeners;

import com.sk89q.worldedit.regions.CuboidRegion;
import net.najtex.myfirstplugin.MyFirstPlugin;
import net.najtex.myfirstplugin.SocketConnection;
import net.najtex.myfirstplugin.minigame.PlayerManager;
import net.najtex.myfirstplugin.minigame.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.graalvm.compiler.core.common.util.Util;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;

import static org.bukkit.Bukkit.getLogger;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        try {
            PlayerManager playerManager = PlayerManager.getPlayerManagerByUUID(player.getUniqueId().toString());

            if (Utils.isLocationInRegion(player.getLocation(), playerManager.teamManager.portalRegion)) {
                getLogger().info("Player " + player.getName() + " entered portal");
            }
        } catch (NullPointerException e) {
            // Player is not in game
        }
    }
}

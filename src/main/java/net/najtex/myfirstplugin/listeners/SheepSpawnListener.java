package net.najtex.myfirstplugin.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import static org.bukkit.Bukkit.broadcastMessage;

public class SheepSpawnListener implements Listener {

    @EventHandler
    public void onSheepSpawn(CreatureSpawnEvent event) {

        Location location = event.getLocation();

        if (event.getEntityType() == EntityType.SHEEP) {
            event.setCancelled(true);

            location.getWorld().setType(location, Material.COBBLESTONE);

            broadcastMessage("A sheep has been spawned!");
        }
    }
}

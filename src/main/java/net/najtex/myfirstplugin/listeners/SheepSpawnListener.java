package net.najtex.myfirstplugin.listeners;

import net.najtex.myfirstplugin.MyFirstPlugin;
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

            String itemName = MyFirstPlugin.config.getString("replace-item");

            location.getWorld().setType(location, Material.getMaterial(itemName));

            broadcastMessage("A sheep has been spawned!");
        }
    }
}

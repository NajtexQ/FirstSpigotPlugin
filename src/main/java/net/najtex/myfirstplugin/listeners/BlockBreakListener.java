package net.najtex.myfirstplugin.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static org.bukkit.Bukkit.broadcastMessage;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();

        if (event.getBlock().getType() == Material.COBBLESTONE) {
            // Drop an item
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.BEEF, 1));
        } else {
            event.setCancelled(true);
        }
    }
}

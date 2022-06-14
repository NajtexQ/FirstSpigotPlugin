package net.najtex.myfirstplugin.listeners;

import net.najtex.myfirstplugin.MyFirstPlugin;
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

        String itemName = MyFirstPlugin.config.getString("replace-item");
        String itemDrop = MyFirstPlugin.config.getString("drop-item");

        if (event.getBlock().getType() == Material.valueOf(itemName)) {
            // Drop an item
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.valueOf(itemDrop), MyFirstPlugin.config.getInt("drop-amount")));
        } else {
            event.setCancelled(true);
        }
    }
}

package net.najtex.myfirstplugin.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.Bukkit.*;

public class WorldManager {

    public static void createWorld(String name) {
        getLogger().info("Creating world...");
        WorldCreator world = new WorldCreator(name);

        world.type(WorldType.FLAT);
        world.generateStructures(false);
        world.createWorld();
    }

    public static void deleteWorld(String name) {
        getLogger().info("Deleting world...");

        // Move all players to the default world
        for (Player player : getOnlinePlayers()) {
            player.teleport(getWorlds().get(0).getSpawnLocation());
        }

        World world = getWorld(name);

        unloadWorld(world, false);
        world.getWorldFolder().delete();

    }
}

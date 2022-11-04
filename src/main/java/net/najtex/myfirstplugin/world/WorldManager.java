package net.najtex.myfirstplugin.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.Bukkit.*;

public class WorldManager {

    public static World createWorld(String name) {
        getLogger().info("Creating world...");
        WorldCreator world = new WorldCreator(name);

        world.type(WorldType.FLAT);
        world.generateStructures(false);

        world.generatorSettings("{\"lakes\":false,\"features\":false,\"layers\":[{\"block\":\"minecraft:air\",\"height\":1}],\"structures\":{\"structures\":{}}}");

        World newWorld = world.createWorld();

        newWorld.setSpawnLocation(0, 70, 0);
        newWorld.getBlockAt(0, 69, 0).setType(Material.BEDROCK);

        return newWorld;
    }

    public static void deleteWorld(String name) {
        getLogger().info("Deleting world...");

        for (Player player : getOnlinePlayers()) {
            player.teleport(getWorlds().get(0).getSpawnLocation());
        }

        World world = getWorld(name);

        unloadWorld(world, false);

        File worldFolder = new File(getWorldContainer(), name);

        if (worldFolder.exists()) {
            deleteWorldFolder(worldFolder);
        }
    }

    private static void deleteWorldFolder(File worldFolder) {
        File[] files = worldFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteWorldFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        worldFolder.delete();
    }
}

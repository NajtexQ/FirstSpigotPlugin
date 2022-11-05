package net.najtex.myfirstplugin.world;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionBuilder;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.*;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.*;

public class WorldManager {

    public static List<String> worldNames = new ArrayList<>();

    public static World createWorld(String name) {
        getLogger().info("Creating world...");
        WorldCreator world = new WorldCreator(name);

        world.type(WorldType.FLAT);
        world.environment(World.Environment.NORMAL);
        world.generateStructures(false);

        world.generatorSettings("{\"lakes\":false,\"features\":false,\"layers\":[{\"block\":\"minecraft:air\",\"height\":1}],\"structures\":{\"structures\":{}}}");

        World newWorld = world.createWorld();

        Location blockLocation = new Location(newWorld, 0, 65, 0);

        newWorld.setSpawnLocation(0, 66, 0);
        newWorld.getBlockAt(blockLocation).setType(Material.BEDROCK);
        newWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        newWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        newWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        newWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

        worldNames.add(name);

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

    public static void pasteSchematic(World world, Location location, String schematic) {

        File file = new File("plugins/MyFirstPlugin/schematics/" + schematic);

        ClipboardFormat format = ClipboardFormats.findByFile(file);

        getLogger().info("Loading schematic...");

        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();

            getLogger().info("Schematic loaded!");

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(world))) {
                getLogger().info("Pasting schematic...");
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                        .build();
                getLogger().info("Schematic pasted!");
                Operations.complete(operation);
            } catch (WorldEditException e) {
                getLogger().info("Error while pasting schematic!");
                throw new RuntimeException(e);
            }
            getLogger().info("Test");
        } catch (FileNotFoundException e) {
            getLogger().info("Error while pasting schematic");
            throw new RuntimeException(e);
        } catch (IOException e) {
            getLogger().info("Error while pasting schematic");
            throw new RuntimeException(e);
        }

        getLogger().info("Did we came here?");

    }

    public static void deleteAllWorlds() {

        if (worldNames.size() > 0) {
            for (String worldName : worldNames) {
                deleteWorld(worldName);
            }
        }
    }

    public static void createSchematicsFolder() {

        File folder = new File("plugins/MyFirstPlugin/schematics");

        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}

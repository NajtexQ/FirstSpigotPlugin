package net.najtex.myfirstplugin.minigame;

import net.najtex.myfirstplugin.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class ArenaManager {

        private static File arenaFile = new File("plugins/MyFirstPlugin/arena.yml");
        private final Set<Arena> arenas = new HashSet<>();

        private static FileConfiguration data;

        public ArenaManager() {
                createArenaFile();
                data = YamlConfiguration.loadConfiguration(arenaFile);

                Location lobbyLocation = new Location(Bukkit.getWorld("world"), 72, 76, 160);

                data.set("arenas.sandtic.lobbyLocation", lobbyLocation.toString());
                try {
                        data.save(arenaFile);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                try {
                        createArenaConfigs();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public Arena createArena(String arenaName, String gameMode, int numOfTeams, boolean isPrivate) {

                Arena arena = new Arena(arenaName, gameMode, numOfTeams, isPrivate);
                arenas.add(arena);

                WorldManager.createWorld(arena.getGameName());

                return arena;
        }

        public void removeArena(Arena arena) {
                arenas.remove(arena);
        }

        public Arena getArena(int gameId) {
                for (Arena arena : arenas) {
                        if (arena.getGameId() == gameId) {
                                return arena;
                        }
                }
                return null;
        }

        public Set<Arena> getArenas() {
                return arenas;
        }

        public void QuickJoin(Player player, String gameMode) {

                boolean foundArena = false;

                for (Arena arena : arenas) {
                        if (arena.getGameMode().equals(gameMode)) {
                                if (arena.spaceAvailable()) {
                                        arena.Join(player);
                                        foundArena = true;
                                        break;
                                }
                        }
                }

                if (!foundArena) {
                        Arena arena = createArena("sandtic", "solo", 1, false);
                        arena.Join(player);
                }
        }

        public static void createArenaFile() {

                if (!arenaFile.exists()) {
                        try {
                                arenaFile.createNewFile();
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
        }

        public void createArenaConfigs() throws IOException {

                for (String arenaSection : data.getConfigurationSection("arenas").getKeys(false)) {

                        getLogger().info("Creating arena config for arena: " + arenaSection);

                        ArenaConfig arenaConfig = new ArenaConfig(arenaSection);

                        arenaConfig.lobbyLocation = data.getString("arenas." + arenaSection + ".lobbyLocation");
                        arenaConfig.endLocation = data.getString("arenas." + arenaSection + ".endLocation");
                        arenaConfig.spectatorLocation = data.getString("arenas." + arenaSection + ".spectatorLocation");
                        arenaConfig.arenaLocation1 = data.getString("arenas." + arenaSection + ".arenaLocation1");
                        arenaConfig.arenaLocation2 = data.getString("arenas." + arenaSection + ".arenaLocation2");
                        arenaConfig.schematicName = data.getString("arenas." + arenaSection + ".schematicName");
                        arenaConfig.arenaHeight = data.getInt("arenas." + arenaSection + ".arenaHeight");

                        for (String baseId : data.getConfigurationSection("arenas." + arenaSection + ".bases").getKeys(false)) {

                                getLogger().info("Creating base config for base: " + baseId);

                                TeamColors baseColor = TeamColors.valueOf(data.getString("arenas." + arenaSection + ".bases." + baseId + ".color"));

                                ArenaBase arenaBase = new ArenaBase(Integer.parseInt(baseId), baseColor);

                                arenaBase.spawnLocation = data.getString("arenas." + arenaSection + ".bases." + baseId + ".spawnLocation");
                                arenaBase.respawnLocation = data.getString("arenas." + arenaSection + ".bases." + baseId + ".respawnLocation");
                                arenaBase.cageLocation1 = data.getString("arenas." + arenaSection + ".bases." + baseId + ".cageLocation1");
                                arenaBase.cageLocation2 = data.getString("arenas." + arenaSection + ".bases." + baseId + ".cageLocation2");
                                arenaBase.portalLocation1 = data.getString("arenas." + arenaSection + ".bases." + baseId + ".portalLocation1");
                                arenaBase.portalLocation2 = data.getString("arenas." + arenaSection + ".bases." + baseId + ".portalLocation2");

                                arenaConfig.arenaBases.put(baseColor, arenaBase);
                        }
                }

        }

        public static String generateRandomString(int n)
        {

                String character = "abcdefghijklmnopqrstuvxyz";

                StringBuilder sb = new StringBuilder(n);

                for (int i = 0; i < n; i++) {
                        int index = (int)(character.length() * Math.random());
                        sb.append(character.charAt(index));
                }

                return sb.toString();
        }
}

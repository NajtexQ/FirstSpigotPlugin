package net.najtex.myfirstplugin.minigame;

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

        private int gameId = 0;

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

        public Arena createArena(String gameName, int numOfTeams, int maxPlayersPerTeam) {
                Arena arena = new Arena(gameId, gameName, numOfTeams, maxPlayersPerTeam);
                arenas.add(arena);
                gameId++;

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

        public void QuickJoin(Player player, String gameName) {

                boolean foundArena = false;

                for (Arena arena : arenas) {
                        if (arena.spaceAvailable()) {
                                arena.Join(player);
                                foundArena = true;
                                break;
                        }
                }

                if (!foundArena) {
                        Arena arena = createArena(gameName, 2, 1);
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

                        arenaConfig.lobbyLocation = stringToLocation(data.getString("arenas." + arenaSection + ".lobbyLocation"));
                        arenaConfig.endLocation = stringToLocation(data.getString("arenas." + arenaSection + ".endLocation"));
                        arenaConfig.spectatorLocation = stringToLocation(data.getString("arenas." + arenaSection + ".spectatorLocation"));
                        arenaConfig.arenaLocation1 = stringToLocation(data.getString("arenas." + arenaSection + ".arenaLocation1"));
                        arenaConfig.arenaLocation2 = stringToLocation(data.getString("arenas." + arenaSection + ".arenaLocation2"));
                        arenaConfig.schematicName = data.getString("arenas." + arenaSection + ".schematicName");
                        arenaConfig.arenaHeight = data.getInt("arenas." + arenaSection + ".arenaHeight");

                        for (String baseId : data.getConfigurationSection("arenas." + arenaSection + ".bases").getKeys(false)) {

                                getLogger().info("Creating base config for base: " + baseId);

                                TeamColors baseColor = TeamColors.valueOf(data.getString("arenas." + arenaSection + ".bases." + baseId + ".color"));

                                ArenaBase arenaBase = new ArenaBase(Integer.parseInt(baseId), baseColor);

                                arenaBase.spawnLocation = stringToLocation(data.getString("arenas." + arenaSection + ".bases." + baseId + ".spawnLocation"));
                                arenaBase.respawnLocation = stringToLocation(data.getString("arenas." + arenaSection + ".bases." + baseId + ".respawnLocation"));
                                arenaBase.cageLocation1 = stringToLocation(data.getString("arenas." + arenaSection + ".bases." + baseId + ".cageLocation1"));
                                arenaBase.cageLocation2 = stringToLocation(data.getString("arenas." + arenaSection + ".bases." + baseId + ".cageLocation2"));
                                arenaBase.portalLocation1 = stringToLocation(data.getString("arenas." + arenaSection + ".bases." + baseId + ".portalLocation1"));
                                arenaBase.portalLocation2 = stringToLocation(data.getString("arenas." + arenaSection + ".bases." + baseId + ".portalLocation2"));

                                arenaConfig.arenaBases.put(baseColor, arenaBase);
                        }
                }

        }

        private Location stringToLocation(String locationString) {
                String[] locationArray = locationString.split(",");
                String worldName = locationArray[0].split("=")[1];
                double x = Double.parseDouble(locationArray[1].split("=")[1]);
                double y = Double.parseDouble(locationArray[2].split("=")[1]);
                double z = Double.parseDouble(locationArray[3].split("=")[1]);
                return new Location(Bukkit.getWorld(worldName), x, y, z);
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

package net.najtex.myfirstplugin.minigame;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ArenaManager {

        private int gameId = 0;
        private final Set<Arena> arenas = new HashSet<>();

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
}

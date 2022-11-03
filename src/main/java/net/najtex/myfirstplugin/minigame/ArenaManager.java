package net.najtex.myfirstplugin.minigame;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ArenaManager {

        private final Set<Arena> arenas = new HashSet<>();

        public void createArena(int gameId, String gameName, int numOfTeams) {
                arenas.add(new Arena(gameId, gameName, numOfTeams));
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
                for (Arena arena : arenas) {
                        if (arena.spaceAvailable()) {
                                arena.Join(player);
                                break;
                        }
                }
        }

}

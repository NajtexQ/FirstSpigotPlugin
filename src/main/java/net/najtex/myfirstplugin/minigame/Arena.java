package net.najtex.myfirstplugin.minigame;

import net.najtex.myfirstplugin.MyFirstPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Arena {

        private int gameId;
        private String gameName;

        private ArenaConfig arenaConfig;

        private final Set<TeamManager> teams = new HashSet<>();

        private int numOfTeams;
        private int maxPlayersPerTeam;

        private Location spawnLocation;
        private Location lobbyLocation;

        private boolean isGameRunning;

        public Arena(int gameId, String gameName, int numOfTeams, int maxPlayersPerTeam) {

                this.arenaConfig = ArenaConfig.getArenaConfig(gameName);

                this.gameId = gameId;
                this.gameName = gameName;
                this.numOfTeams = numOfTeams;
                this.maxPlayersPerTeam = maxPlayersPerTeam;

                this.spawnLocation = arenaConfig.endLocation;
                this.lobbyLocation = arenaConfig.lobbyLocation;

                createTeams(maxPlayersPerTeam);
        }

        public int getGameId() {
                return gameId;
        }

        public String getGameName() { return gameName; }

        public Location getLobbyLocation() { return lobbyLocation; }

        public boolean spaceAvailable() {
                int playersInArena = 0;
                for (TeamManager team : teams) {
                        playersInArena += team.getPlayers().size();
                }
                return playersInArena < numOfTeams * maxPlayersPerTeam;
        }

        public void createTeams(int maxPlayersPerTeam) {
                this.maxPlayersPerTeam = maxPlayersPerTeam;
                for (int i = 0; i < numOfTeams; i++) {
                        teams.add(new TeamManager(TeamColors.values()[i].name(), TeamColors.values()[i].name(), maxPlayersPerTeam));
                }
        }

        public void addPlayer(Player player) {
                TeamManager teamWithLeastPlayers = null;
                for (TeamManager team : teams) {
                        if (teamWithLeastPlayers == null) {
                                teamWithLeastPlayers = team;
                        } else if (team.getPlayers().size() < teamWithLeastPlayers.getPlayers().size()) {
                                teamWithLeastPlayers = team;
                        }
                }
                teamWithLeastPlayers.addPlayer(new PlayerManager(player));
        }

        public void Join(Player player) {
                addPlayer(player);
                player.teleport(lobbyLocation);
                PlayerManager.getPlayerManagerByUUID(player.getUniqueId().toString()).isInGame = true;

                player.sendTitle(ChatColor.BLUE + "Waiting for other players", "This is a test.", 1, 20, 1);

                sendMessageToAllPlayers(ChatColor.BLUE + player.getName() + " has joined the game.");

                if (spaceAvailable()) {
                        sendMessageToAllPlayers(ChatColor.BLUE + "Waiting for other players to join...");
                } else {
                        changeState(GameState.STARTING);
                }
        }

        public Arena changeState(GameState gameState) {
                switch (gameState) {
                        case LOBBY:
                                isGameRunning = false;
                                break;
                        case STARTING:
                                isGameRunning = true;

                                for (int i = 10; i > 0; i--) {
                                        final int time = i;
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(MyFirstPlugin.getPlugin(MyFirstPlugin.class), new Runnable() {
                                                @Override
                                                public void run() {
                                                        sendMessageToAllPlayers(ChatColor.BLUE + "Game starting in " + time + " seconds.");
                                                }
                                        }, 20 * (10 - i));

                                }
                                Bukkit.getScheduler().scheduleSyncDelayedTask(MyFirstPlugin.getPlugin(MyFirstPlugin.class), new Runnable() {
                                        @Override
                                        public void run() {
                                                changeState(GameState.RUNNING);
                                        }
                                }, 20*10);
                                break;
                        case RUNNING:
                                isGameRunning = true;
                                for (TeamManager team : teams) {
                                        for (PlayerManager player : team.getPlayers()) {
                                                player.getPlayer().teleport(spawnLocation);
                                                player.getPlayer().sendTitle(ChatColor.BLUE + "Game started!", "This is a test.", 1, 20, 1);
                                        }
                                }
                                break;
                        case END:
                                isGameRunning = false;
                                break;
                }
                return this;
        }

        public void sendMessageToAllPlayers(String message) {
                for (TeamManager team : teams) {
                        for (PlayerManager player : team.getPlayers()) {
                                player.getPlayer().sendMessage(message);
                        }
                }
        }
}

package net.najtex.myfirstplugin.minigame;

import net.najtex.myfirstplugin.MyFirstPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class Arena {
        private static int gameId = 0;
        private String gameName;

        private boolean isPrivate;

        private ArenaConfig arenaConfig;

        private final Set<TeamManager> teams = new HashSet<>();

        private int numOfTeams;
        private String gameMode;
        private int maxPlayersPerTeam;

        private boolean isGameRunning;

        public Location lobbyLocation;
        public Location endLocation;
        public Location spectatorLocation;
        public Location arenaLocation1;
        public Location arenaLocation2;

        public Arena(String arenaName, String gameMode, int numOfTeams, boolean isPrivate) {

                this.arenaConfig = ArenaConfig.getArenaConfig(arenaName);
                this.numOfTeams = numOfTeams;
                this.isPrivate = isPrivate;

                this.lobbyLocation = Utils.newWorldStringToLocation(arenaName, arenaConfig.lobbyLocation);
                this.endLocation = Utils.newWorldStringToLocation(arenaName, arenaConfig.endLocation);
                this.spectatorLocation = Utils.newWorldStringToLocation(arenaName, arenaConfig.spectatorLocation);
                this.arenaLocation1 = Utils.newWorldStringToLocation(arenaName, arenaConfig.arenaLocation1);
                this.arenaLocation2 = Utils.newWorldStringToLocation(arenaName, arenaConfig.arenaLocation2);

                setGameMode(gameMode);

                String randomString = ArenaManager.generateRandomString(8);
                this.gameName = arenaConfig.arenaName + "_" +  randomString;

                getLogger().info("Arena name: " + this.gameName);

                createTeams(maxPlayersPerTeam);

                gameId++;
        }

        public int getGameId() {
                return gameId;
        }

        public String getGameName() { return gameName; }

        public String getGameMode() { return this.gameMode; }

        public boolean getIsRunning() { return this.isGameRunning; }
        public boolean getIsPrivate() { return this.isPrivate; }

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
                teamWithLeastPlayers.addPlayer(new PlayerManager(player, teamWithLeastPlayers));
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
                                                player.getPlayer().teleport(arenaLocation1);
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

        private void setGameMode(String gameMode) {

                this.gameMode = gameMode;

                switch (gameMode) {
                        case "solo":
                                this.maxPlayersPerTeam = 1;
                                break;
                        case "duo":
                                this.maxPlayersPerTeam = 2;
                                break;
                        case "squad":
                                this.maxPlayersPerTeam = 4;
                                break;
                        default:
                                this.maxPlayersPerTeam = 1;
                                break;
                }
        }
}

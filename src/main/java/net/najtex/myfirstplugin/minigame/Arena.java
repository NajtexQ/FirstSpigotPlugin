package net.najtex.myfirstplugin.minigame;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import net.najtex.myfirstplugin.MyFirstPlugin;
import net.najtex.myfirstplugin.data.PlayerData;
import net.najtex.myfirstplugin.sternalBoard.SternalBoardHandler;
import net.najtex.myfirstplugin.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import scala.concurrent.impl.FutureConvertersImpl;
import sun.util.resources.cldr.ext.CurrencyNames_vai;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class Arena {
        private static int gameId = 0;
        private String gameName;

        private boolean isPrivate;

        private ArenaConfig arenaConfig;

        private final Set<TeamManager> teams = new HashSet<>();
        public static final Set<SternalBoardHandler> scoreBoards = new HashSet<>();

        public final HashMap<TeamColors, CuboidRegion> portalRegions = new HashMap<>();

        private int numOfTeams;
        private String gameMode;
        private int maxPlayersPerTeam;

        private boolean isGameRunning;

        public Location lobbyLocation;
        public Location endLocation;
        public Location spectatorLocation;
        public Location arenaLocation1;
        public Location arenaLocation2;

        public Location arenaPasteLocation;

        public Arena(String arenaName, String gameMode, int numOfTeams, boolean isPrivate) {

                this.arenaConfig = ArenaConfig.getArenaConfig(arenaName);
                getLogger().info("Number of teams: " + numOfTeams);
                this.numOfTeams = numOfTeams;
                this.isPrivate = isPrivate;

                String randomString = ArenaManager.generateRandomString(8);
                this.gameName = arenaName + "_" +  randomString;

                this.arenaPasteLocation = new Location(Bukkit.getWorld(this.gameName), 0, 65, 0);

                getLogger().info("Schematic name: " + this.arenaConfig.schematicName);

                World newWorld = WorldManager.createWorld(this.gameName);
                WorldManager.pasteSchematic(newWorld, arenaPasteLocation, this.arenaConfig.schematicName);

                this.lobbyLocation = Utils.newWorldStringToLocation(this.gameName, arenaConfig.lobbyLocation);
                this.endLocation = Utils.newWorldStringToLocation(this.gameName, arenaConfig.endLocation);
                this.spectatorLocation = Utils.newWorldStringToLocation(this.gameName, arenaConfig.spectatorLocation);
                this.arenaLocation1 = Utils.newWorldStringToLocation(this.gameName, arenaConfig.arenaLocation1);
                this.arenaLocation2 = Utils.newWorldStringToLocation(this.gameName, arenaConfig.arenaLocation2);

                setGameMode(gameMode);

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

        public ArenaConfig getArenaConfig() { return this.arenaConfig; }
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
                        getLogger().info("Creating team " + i);
                        teams.add(new TeamManager(this, TeamColors.values()[i].name(), TeamColors.values()[i].name(), maxPlayersPerTeam));
                }

                for (TeamManager team : teams) {
                        getLogger().info("Team name: " + team.getTeamName());
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
                teamWithLeastPlayers.addPlayer(new PlayerManager(player, teamWithLeastPlayers, this));
        }

        public void Join(Player player) {
                addPlayer(player);

                getLogger().info("Player " + player.getName() + " joined arena " + this.gameName);
                getLogger().info("Teleporting to: " + this.lobbyLocation.toString());
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
                                setScoreBoards();
                                for (TeamManager team : teams) {
                                        for (PlayerManager player : team.getPlayers()) {
                                                player.getPlayer().teleport(arenaLocation1);
                                                player.getPlayer().sendTitle(ChatColor.BLUE + "Game started!", "This is a test.", 1, 20, 1);
                                        }
                                }
                                startTimer();
                                break;
                        case END:
                                isGameRunning = false;
                                for (TeamManager team : teams) {
                                        for (PlayerManager player : team.getPlayers()) {
                                                PlayerData playerData = new PlayerData(player.getPlayer());
                                                playerData.updateNewStats(player.getPlayerKills(), player.getPlayerDeaths(), player.getPlayerScore(), true);
                                                player.getPlayer().teleport(endLocation);
                                                player.getPlayer().sendTitle(ChatColor.BLUE + "Game ended!", "This is a test.", 1, 20, 1);
                                                WorldManager.deleteWorld(this.gameName);
                                        }
                                }

                                ArenaManager.removeArena(this);

                                getLogger().info("Arena " + this.gameName + " has been removed.");

                                for (SternalBoardHandler scoreBoard : scoreBoards) {
                                        scoreBoard.delete();
                                }

                                getLogger().info("Scoreboards have been removed.");

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

        private void setScoreBoards() {
                for (TeamManager team : teams) {
                        for (PlayerManager player : team.getPlayers()) {
                                SternalBoardHandler scoreBoard = player.boardHandler;

                                scoreBoard.updateTitle(ChatColor.GOLD + "TheBridge");

                                TeamManager redTeam = getTeamByColor("RED");
                                TeamManager blueTeam = getTeamByColor("BLUE");

                                scoreBoard.updateLines(
                                        " ",
                                        ChatColor.WHITE + "Time: " + ChatColor.GRAY + "00:00",
                                        " ",
                                        redTeam != null ? ChatColor.RED + redTeam.getTeamName() + ": " + ChatColor.WHITE + redTeam.getTeamScore() : "",
                                        blueTeam != null ? ChatColor.BLUE + blueTeam.getTeamName() + ": " + ChatColor.WHITE + blueTeam.getTeamScore() : "",
                                        " ",
                                        ChatColor.GRAY + "-----------",
                                        " ",
                                        ChatColor.WHITE + "Kills: " + ChatColor.GRAY + player.getPlayerKills(),
                                        ChatColor.WHITE + "Deaths: " + ChatColor.GRAY + player.getPlayerDeaths(),
                                        ChatColor.WHITE + "Score: " + ChatColor.GRAY + player.getPlayerScore(),
                                        " ",
                                        ChatColor.GRAY + "-----------"
                                );
                        }
                }
        }

        public void updateScoreBoardPoints() {
                for (SternalBoardHandler scoreBoard : scoreBoards) {
                        scoreBoard.updateLine(3, ChatColor.RED + getTeamByColor("RED").getTeamName() + ": " + ChatColor.WHITE + getTeamByColor("RED").getTeamScore());
                        scoreBoard.updateLine(4, ChatColor.BLUE + getTeamByColor("BLUE").getTeamName() + ": " + ChatColor.WHITE + getTeamByColor("BLUE").getTeamScore());
                }
        }

        private TeamManager getTeamByColor(String teamColor) {
                for (TeamManager team : teams) {
                        if (team.getTeamColor().equals(teamColor)) {
                                return team;
                        }
                }
                return null;
        }

        private void startTimer() {
                new BukkitRunnable() {
                        int time = 10;

                        @Override
                        public void run() {
                                if (time <= 0) {
                                        changeState(GameState.END);
                                        cancel();
                                } else {
                                        for (TeamManager team : teams) {
                                                for (PlayerManager player : team.getPlayers()) {
                                                        SternalBoardHandler scoreBoard = player.boardHandler;
                                                        scoreBoard.updateLine(1, ChatColor.WHITE + "Time: " + ChatColor.GRAY + String.format("%02d:%02d", time / 60, time % 60));
                                                }
                                        }
                                        time--;
                                        getLogger().info("Time: " + time);
                                }
                        }
                }.runTaskTimer(MyFirstPlugin.getPlugin(MyFirstPlugin.class), 0, 20);

        }

        public void scoredGoal() {
                for (TeamManager team : teams) {
                        for (PlayerManager player : team.getPlayers()) {
                                player.getPlayer().teleport(player.teamManager.respawnLocation);
                                player.getPlayer().sendTitle(ChatColor.BLUE + "Goal scored!", "This is a test.", 1, 20, 1);
                        }
                }
        }
}

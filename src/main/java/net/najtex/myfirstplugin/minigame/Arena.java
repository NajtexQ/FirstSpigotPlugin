package net.najtex.myfirstplugin.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Arena {

        private int gameId;
        private String gameName;

        private final Set<TeamManager> teams = new HashSet<>();

        private int numOfTeams;
        private int maxPlayersPerTeam;

        private Location spawnLocation;
        private Location lobbyLocation;

        private boolean isGameRunning;

        public Arena(int gameId, String gameName, int numOfTeams) {
                this.gameId = gameId;
                this.gameName = gameName;
                this.numOfTeams = numOfTeams;
                this.maxPlayersPerTeam = 2;

                World world = Bukkit.getWorld("world");

                this.spawnLocation = new Location(world, 72, 76, 136);

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
                player.teleport(spawnLocation);
                PlayerManager.getPlayerManagerByUUID(player.getUniqueId().toString()).isInGame = true;
        }

}

package net.najtex.myfirstplugin.minigame;

import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private String teamName;
    private String teamColor;

    public Arena arena;

    private final Set<PlayerManager> players = new HashSet<>();

    private int minPlayers;
    private int maxPlayers;

    public Location spawnLocation;
    public Location respawnLocation;
    private Location cageLocation1;
    private Location cageLocation2;
    private Location portalLocation1;
    private Location portalLocation2;

    public CuboidRegion cageRegion;
    public CuboidRegion portalRegion;

    private ArenaBase arenaBase;

    private int teamScore;

    public TeamManager(Arena arena, String teamName, String teamColor, int maxPlayers) {
        this.teamName = teamName;
        this.teamColor = teamColor;
        this.maxPlayers = maxPlayers;
        this.minPlayers = (int) Math.floor(maxPlayers * 0.75);
        this.arena = arena;

        this.arenaBase = arena.getArenaConfig().getArenaBase(TeamColors.valueOf(teamColor));

        String arenaName = arena.getGameName();

        this.spawnLocation = Utils.newWorldStringToLocation(arenaName, arenaBase.spawnLocation);
        this.respawnLocation = Utils.newWorldStringToLocation(arenaName, arenaBase.respawnLocation);
        this.cageLocation1 = Utils.newWorldStringToLocation(arenaName, arenaBase.cageLocation1);
        this.cageLocation2 = Utils.newWorldStringToLocation(arenaName, arenaBase.cageLocation2);
        this.portalLocation1 = Utils.newWorldStringToLocation(arenaName, arenaBase.portalLocation1);
        this.portalLocation2 = Utils.newWorldStringToLocation(arenaName, arenaBase.portalLocation2);

        this.cageRegion = Utils.createCuboid(cageLocation1, cageLocation2);
        this.portalRegion = Utils.createCuboid(portalLocation1, portalLocation2);

        this.arena.portalRegions.put(TeamColors.valueOf(this.teamColor), portalRegion);
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public Set<PlayerManager> getPlayers() {
        return players;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getTeamKills() {
        int teamKills = 0;
        for (PlayerManager player : players) {
            teamKills += player.getPlayerKills();
        }
        return teamKills;
    }

    public void addPlayer(PlayerManager player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void addTeamScore(PlayerManager player) {
        this.teamScore += 1;
        player.addPlayerScore(1);
    }

    public void setArenaBase(ArenaBase arenaBase) {
        this.arenaBase = arenaBase;
    }

}

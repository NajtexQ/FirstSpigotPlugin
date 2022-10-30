package net.najtex.myfirstplugin.minigame;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private String teamName;
    private String teamColor;

    private final Set<PlayerManager> players = new HashSet<>();

    private int minPlayers;
    private int maxPlayers;

    private int teamScore;

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

    public void addTeamScore(int score) {
        this.teamScore += score;
    }
}

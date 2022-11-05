package net.najtex.myfirstplugin.minigame;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private Player player;
    private String playerName;
    private String playerUUID;

    public static List<PlayerManager> playerManagerList = new ArrayList<>();

    public TeamManager teamManager;

    public boolean isInGame;
    private int playerScore;
    private int playerKills;
    private int playerDeaths;

    public PlayerManager(Player player, TeamManager teamManager) {
        this.player = player;
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId().toString();
        this.teamManager = teamManager;
        playerManagerList.add(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player getPlayer() { return player; }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getPlayerKills() {
        return playerKills;
    }
    public int getPlayerDeaths() { return playerDeaths; }

    public double getPlayerKD() {
        return (double) playerKills / (double) playerDeaths;
    }

    public void addPlayerScore(int score) {
        this.playerScore += score;
    }

    public void addPlayerKills(int kills) {
        this.playerKills += kills;
    }
    public void addPlayerDeaths(int deaths) { this.playerDeaths += deaths; }

    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public void incrementPlayerKills() { this.playerKills++; }
    public void incrementPlayerDeaths() { this.playerDeaths++; }

    public static PlayerManager getPlayerManagerByUUID(String uuid) {
        for (PlayerManager player : playerManagerList) {
            if (player.getPlayerUUID().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

}

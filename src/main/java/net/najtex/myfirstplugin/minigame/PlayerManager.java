package net.najtex.myfirstplugin.minigame;

import org.bukkit.entity.Player;

import java.util.List;

public class PlayerManager {

    private Player player;
    private String playerName;
    private String playerUUID;

    public static List<PlayerManager> playerManagerList;

    private int playerScore;
    private int playerKills;
    private int playerDeaths;

    public PlayerManager(Player player) {
        this.player = player;
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId().toString();
        playerManagerList.add(this);
    }

    public String getPlayerName() {
        return playerName;
    }

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

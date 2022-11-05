package net.najtex.myfirstplugin.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerData {

    File config;
    FileConfiguration data;

    public PlayerData(Player p) {
        config = new File("plugins/MyFirstPlugin/players/" + p.getUniqueId() + ".yml");
        data = YamlConfiguration.loadConfiguration(config);
    }

    public void setKills(int kills) {
        data.set("kills", kills);
    }

    public void setDeaths(int deaths) {
        data.set("deaths", deaths);
    }

    public void setScores(int scores) {
        data.set("scores", scores);
    }

    public void setPlayedGames(int playedGames) {
        data.set("playedGames", playedGames);
    }

    public void setWins(int wins) {
        data.set("wins", wins);
    }

    public int getKills() {
        return data.getInt("kills");
    }

    public int getDeaths() {
        return data.getInt("deaths");
    }

    public int getScores() {
        return data.getInt("scores");
    }

    public int getPlayedGames() {
        return data.getInt("playedGames");
    }

    public int getWins() {
        return data.getInt("wins");
    }
}

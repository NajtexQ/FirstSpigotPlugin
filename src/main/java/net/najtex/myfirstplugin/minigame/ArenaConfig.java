package net.najtex.myfirstplugin.minigame;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ArenaConfig {
    public String arenaName;
    public String lobbyLocation;
    public String endLocation;
    public String spectatorLocation;
    public String arenaLocation1;
    public String arenaLocation2;
    public String schematicName;
    public int arenaHeight;

    public boolean isDone;

    public HashMap<TeamColors, ArenaBase> arenaBases = new HashMap<>();

    public static HashMap<String, ArenaConfig> arenaConfigs = new HashMap<>();

    public ArenaConfig(String arenaName) {
        this.arenaName = arenaName;

        arenaConfigs.put(arenaName, this);
    }

    public static ArenaConfig getArenaConfig(String arenaName) {
        return arenaConfigs.get(arenaName);
    }
}

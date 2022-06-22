package net.najtex.myfirstplugin.minigame;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private String teamName;

    private final Set<Player> players = new HashSet<>();

    private int minPlayers;
    private int maxPlayers;

    private int teamScore;

    private int teamKills;

}

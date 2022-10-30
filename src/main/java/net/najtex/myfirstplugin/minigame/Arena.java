package net.najtex.myfirstplugin.minigame;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Arena {

        private int gameId;
        private String gameName;

        private final Set<TeamManager> teams = new HashSet<>();

        private int numOfTeams;

        private Location spawnLocation;
        private Location lobbyLocation;

}

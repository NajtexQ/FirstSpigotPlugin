package net.najtex.myfirstplugin.minigame;

import org.bukkit.Location;

public class ArenaBase {

    public int baseId;
    public TeamColors teamColor;
    public Location spawnLocation;
    public Location respawnLocation;
    public Location cageLocation1;
    public Location cageLocation2;
    public Location portalLocation1;
    public Location portalLocation2;

    public ArenaBase(int baseId, TeamColors teamColor) {
        this.baseId = baseId;
        this.teamColor = teamColor;
    }
}

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

    public ArenaBase(int baseId, TeamColors teamColor, Location spawnLocation, Location respawnLocation, Location cageLocation1, Location cageLocation2, Location portalLocation1, Location portalLocation2) {
        this.baseId = baseId;
        this.teamColor = teamColor;
        this.spawnLocation = spawnLocation;
        this.respawnLocation = respawnLocation;
        this.cageLocation1 = cageLocation1;
        this.cageLocation2 = cageLocation2;
        this.portalLocation1 = portalLocation1;
        this.portalLocation2 = portalLocation2;
    }
}

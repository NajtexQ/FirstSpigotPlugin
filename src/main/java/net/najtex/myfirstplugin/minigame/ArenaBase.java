package net.najtex.myfirstplugin.minigame;

import org.bukkit.Location;

public class ArenaBase {

    public int baseId;
    public TeamColors teamColor;
    public String spawnLocation;
    public String respawnLocation;
    public String cageLocation1;
    public String cageLocation2;
    public String portalLocation1;
    public String portalLocation2;

    public ArenaBase(int baseId, TeamColors teamColor) {
        this.baseId = baseId;
        this.teamColor = teamColor;
    }
}

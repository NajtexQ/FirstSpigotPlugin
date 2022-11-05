package net.najtex.myfirstplugin.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {

    public static String locationToString(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location newWorldStringToLocation(String worldName, String string) {

        String[] split = string.split(",");
        float x = Float.parseFloat(split[0]);
        float y = Float.parseFloat(split[1]);
        float z = Float.parseFloat(split[2]);
        float yaw = Float.parseFloat(split[3]);
        float pitch = Float.parseFloat(split[4]);

        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

}

package net.najtex.myfirstplugin.minigame;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import static org.bukkit.Bukkit.getLogger;

public class Utils {

    public static String locationToString(Location location) {
        return location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location newWorldStringToLocation(String worldName, String string) {

        String[] split = string.split(",");
        float x = Float.parseFloat(split[0]);
        float y = Float.parseFloat(split[1]);
        float z = Float.parseFloat(split[2]);
        float yaw = Float.parseFloat(split[3]);
        float pitch = Float.parseFloat(split[4]);
        World world = Bukkit.getWorld(worldName);

        getLogger().info("World name: " + worldName);
        getLogger().info(new Location(world, x, y, z, yaw, pitch).toString());
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static CuboidRegion createCuboid(Location location1, Location location2) {

        BlockVector3 min = BlockVector3.at(location1.getX(), location1.getY(), location1.getZ());
        BlockVector3 max = BlockVector3.at(location2.getX(), location2.getY(), location2.getZ());

        return new CuboidRegion(min, max);
    }

    public static boolean isLocationInRegion(Location location, CuboidRegion region) {
        return region.contains(BlockVector3.at(location.getX(), location.getY(), location.getZ()));
    }

}

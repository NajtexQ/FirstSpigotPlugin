package net.najtex.myfirstplugin;

import net.najtex.myfirstplugin.listeners.BlockBreakListener;
import net.najtex.myfirstplugin.listeners.SheepSpawnListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.broadcastMessage;

public final class MyFirstPlugin extends JavaPlugin {

    public static FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MyFirstPlugin is now enabled!");

        config = getConfig();

        config.options().copyDefaults(false);
        saveDefaultConfig();

        getLogger().info("Config loaded!");

        SocketConnection socketConnection = new SocketConnection();
        try {
            socketConnection.startConnection("localhost", 5000);
            String resp = socketConnection.sendMessage("Hello World!");
            getLogger().info(resp);
            socketConnection.stopConnection();
        } catch (Exception e) {
            getLogger().info("Error connecting to server!");
        }

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new SheepSpawnListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> broadcastMessage("Hello world!"), 0L, 6000L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("MyFirstPlugin is now disabled!");
    }
}

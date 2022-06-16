package net.najtex.myfirstplugin;

import net.najtex.myfirstplugin.listeners.BlockBreakListener;
import net.najtex.myfirstplugin.listeners.PlayerMove;
import net.najtex.myfirstplugin.listeners.SheepSpawnListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

import static org.bukkit.Bukkit.broadcastMessage;

public final class MyFirstPlugin extends JavaPlugin {

    public static FileConfiguration config;
    public static SocketConnection socketConnection;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MyFirstPlugin is now enabled!");

        config = getConfig();

        config.options().copyDefaults(false);
        saveDefaultConfig();

        getLogger().info("Config loaded!");

        socketConnection = new SocketConnection();

        getLogger().info("SocketConnection loaded!");

        try {

            getLogger().info("Connecting to server...");
            socketConnection.startConnection("localhost", 5000);
            getLogger().info("Connected to server!");

        } catch (Exception e) {
            getLogger().info("Error connecting to server!");
        }

        PluginManager pluginManager = getServer().getPluginManager();

        getLogger().info("Registering listeners!");

        pluginManager.registerEvents(new SheepSpawnListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);

        getLogger().info("Listeners registered!");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () ->
        {
            try {
                socketConnection.sendMessage("Ping!");
                getLogger().info("Server responded!");
            } catch (IOException e) {
                getLogger().info("Error sending message to server!");
            }
        }, 0L, 20L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("MyFirstPlugin is now disabled!");
        try {
            socketConnection.stopConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

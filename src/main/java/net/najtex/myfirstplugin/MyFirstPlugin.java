package net.najtex.myfirstplugin;

import net.najtex.myfirstplugin.commands.*;
import net.najtex.myfirstplugin.listeners.BlockBreakListener;
import net.najtex.myfirstplugin.listeners.PlayerKilled;
import net.najtex.myfirstplugin.listeners.PlayerMove;
import net.najtex.myfirstplugin.listeners.SheepSpawnListener;
import net.najtex.myfirstplugin.minigame.ArenaManager;
import net.najtex.myfirstplugin.world.WorldManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

import static org.bukkit.Bukkit.broadcastMessage;

public final class MyFirstPlugin extends JavaPlugin {

    public static FileConfiguration config;
    public static SocketConnection socketConnection;

    public static ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MyFirstPlugin is now enabled!");

        config = getConfig();

        config.options().copyDefaults(false);
        saveDefaultConfig();

        getLogger().info("Config loaded!");

        //socketConnection = new SocketConnection();

        getLogger().info("SocketConnection loaded!");

        arenaManager = new ArenaManager();

        WorldManager.createSchematicsFolder();


        //try {
//
        //    getLogger().info("Connecting to server...");
        //    socketConnection.startConnection("localhost", 5000);
        //    getLogger().info("Connected to server!");
//
        //} catch (Exception e) {
        //    getLogger().info("Error connecting to server!");
        //}

        PluginManager pluginManager = getServer().getPluginManager();

        getLogger().info("Registering listeners!");

        pluginManager.registerEvents(new SheepSpawnListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new PlayerMove(), this);
        pluginManager.registerEvents(new PlayerKilled(), this);

        getLogger().info("Listeners registered!");

        getCommand("heal").setExecutor(new Heal());
        getCommand("healthstats").setExecutor(new HealthStats());
        getCommand("createworld").setExecutor(new CreateWorld());
        getCommand("deleteworld").setExecutor(new DeleteWorld());
        getCommand("worldtp").setExecutor(new WorldTeleport());
        getCommand("quickjoin").setExecutor(new QuickJoin());
        getCommand("createarena").setExecutor(new CreateArena());

        //getServer().getScheduler().scheduleSyncRepeatingTask(this, () ->
        //{
        //    try {
        //        socketConnection.sendMessage("Ping!");
        //        getLogger().info("Server responded!");
        //    } catch (IOException e) {
        //        getLogger().info("Error sending message to server!");
        //    }
        //}, 0L, 1200L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        WorldManager.deleteAllWorlds();

        getLogger().info("MyFirstPlugin is now disabled!");
        //try {
        //    socketConnection.stopConnection();
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}
    }
}

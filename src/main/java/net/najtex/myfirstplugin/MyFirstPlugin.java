package net.najtex.myfirstplugin;

import net.najtex.myfirstplugin.listeners.BlockBreakListener;
import net.najtex.myfirstplugin.listeners.SheepSpawnListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyFirstPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("My first plugin has been enabled!");

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new SheepSpawnListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("My first plugin has been disabled!");
    }
}

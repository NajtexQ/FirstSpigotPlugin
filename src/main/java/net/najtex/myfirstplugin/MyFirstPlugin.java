package net.najtex.myfirstplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class MyFirstPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("My first plugin has been enabled!");

        getServer().getPluginManager().registerEvents(new SheepSpawnListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("My first plugin has been disabled!");
    }
}

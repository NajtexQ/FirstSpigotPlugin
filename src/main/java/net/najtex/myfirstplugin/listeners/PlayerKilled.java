package net.najtex.myfirstplugin.listeners;

import net.najtex.myfirstplugin.minigame.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKilled implements Listener {

    @EventHandler()
    public void onPlayerKilled(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        PlayerKilledEvent(player, killer);

        player.sendMessage("You were killed by " + killer.getName());

        killer.sendMessage("You killed " + player.getName() + " with a K/D ratio of " + PlayerManager.getPlayerManagerByUUID(killer.getUniqueId().toString()).getPlayerKD());
    }

    private void PlayerKilledEvent(Player player, Player killer) {

        PlayerManager playerManager = PlayerManager.getPlayerManagerByUUID(player.getUniqueId().toString());
        playerManager.incrementPlayerDeaths();

        if (killer != null) {
            PlayerManager killerManager = PlayerManager.getPlayerManagerByUUID(killer.getUniqueId().toString());
            killerManager.incrementPlayerKills();
        }
    }
}

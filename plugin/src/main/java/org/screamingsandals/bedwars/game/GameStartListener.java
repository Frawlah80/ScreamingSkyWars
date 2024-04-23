package org.screamingsandals.bedwars.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import org.screamingsandals.bedwars.api.events.BedwarsGameStartedEvent;

import static org.screamingsandals.bedwars.Main.plugin;

public class GameStartListener implements Listener {

    @EventHandler
    public void onGameStart(BedwarsGameStartedEvent e) {
        new BukkitRunnable() {

            @Override
            public void run() {

                Game game = (Game) e.getGame();
                game.setBedDestroyed();

            }

        }.runTaskLater(plugin, 0L);

    }

}

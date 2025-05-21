package org.screamingsandals.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerJoinedEvent;
import org.screamingsandals.bedwars.api.game.Game;

public class GameTeleportCageListener implements Listener {

    @EventHandler
    public void onPlayerGameJoin(BedwarsPlayerJoinedEvent bpje) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            Player player = bpje.getPlayer();
            Game game = bpje.getGame();
            org.screamingsandals.bedwars.api.Team team = game.getTeamOfPlayer(player);
            Location loc = team.getTeamSpawn();
            player.teleport(loc);
        }, 5L);
    }

}

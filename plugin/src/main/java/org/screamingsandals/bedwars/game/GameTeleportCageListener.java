package org.screamingsandals.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDamageEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.events.BedwarsGameChangedStatusEvent;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerJoinedEvent;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerLeaveEvent;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.api.Team;
import org.screamingsandals.bedwars.utils.MiscUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GameTeleportCageListener implements Listener {

    private final Set<UUID> fallDamageImmune = new HashSet<>();

    @EventHandler
    public void onPlayerGameJoin(BedwarsPlayerJoinedEvent playerJoining) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            Player player = playerJoining.getPlayer();
            Game game = playerJoining.getGame();
            org.screamingsandals.bedwars.api.Team team = game.getTeamOfPlayer(player);
            Location loc = team.getTeamSpawn();
            player.teleport(loc);
            fallDamageImmune.add(player.getUniqueId());
        }, 5L);
    }

    @EventHandler
    public void onPlayerGameLeave(BedwarsPlayerLeaveEvent playerLeaving) {
        Player player = playerLeaving.getPlayer();
        if (fallDamageImmune.contains(player.getUniqueId())) {
            fallDamageImmune.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onGameRebuild(BedwarsGameChangedStatusEvent reBuilding) {
        Game game = reBuilding.getGame();
        List<Team> teams = game.getAvailableTeams();
        if (game.getStatus() == GameStatus.REBUILDING) {
            Bukkit.getLogger().info("Making cages");
            for (Team t : teams) {
                Location teamSpawnLoc = MiscUtils.readLocationFromString(game.getGameWorld(), "teams." + t.getName() + ".spawn");
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    buildCage(teamSpawnLoc);
                });
            }
        }
    }

    @EventHandler
    public void onGameStart(BedwarsGameChangedStatusEvent gameStarted) {
        Game game = gameStarted.getGame();
        List<Team> teams = game.getAvailableTeams();
        if (game.getStatus() == GameStatus.RUNNING) {
            Bukkit.getLogger().info("Removing cages");
            for (Team t : teams) {
                Location teamSpawnLoc = MiscUtils.readLocationFromString(game.getGameWorld(), "teams." + t.getName() + ".spawn");
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    removeCage(teamSpawnLoc);
                });
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent dmgEvent) {
        if (dmgEvent.getEntity() instanceof Player) {
            Player player = (Player) dmgEvent.getEntity();

            if (dmgEvent.getCause() == EntityDamageEvent.DamageCause.FALL &&
                    fallDamageImmune.contains(player.getUniqueId())) {

                dmgEvent.setCancelled(true);
                fallDamageImmune.remove(player.getUniqueId());
            }
        }
    }

    public static void buildCage(Location loc) {
        World world = loc.getWorld();
        int centerX = loc.getBlockX();
        int centerY = loc.getBlockY();
        int centerZ = loc.getBlockZ();

        for (int x = centerX - 1; x <= centerX + 1; x++) {
            for (int y = centerY - 1; y <= centerY + 1; y++) {
                for (int z = centerZ - 1; z <= centerZ + 1; z++) {
                    if (x == centerX - 1 || x == centerX + 1 ||
                            y == centerY - 1 || y == centerY + 1 ||
                            z == centerZ - 1 || z == centerZ + 1) {
                        Location blockLoc = new Location(world, x, y, z);
                        world.getBlockAt(blockLoc).setType(Material.GLASS);
                    }
                }
            }
        }
    }

    public static void removeCage(Location loc) {
        World world = loc.getWorld();
        int centerX = loc.getBlockX();
        int centerY = loc.getBlockY();
        int centerZ = loc.getBlockZ();

        for (int x = centerX - 1; x <= centerX + 1; x++) {
            for (int y = centerY - 1; y <= centerY + 1; y++) {
                for (int z = centerZ - 1; z <= centerZ + 1; z++) {
                    if (x == centerX - 1 || x == centerX + 1 ||
                            y == centerY - 1 || y == centerY + 1 ||
                            z == centerZ - 1 || z == centerZ + 1) {
                        Location blockLoc = new Location(world, x, y, z);
                        world.getBlockAt(blockLoc).setType(Material.AIR);
                    }
                }
            }
        }
    }
}

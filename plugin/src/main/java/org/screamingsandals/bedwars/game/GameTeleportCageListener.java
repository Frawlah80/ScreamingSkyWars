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
import org.screamingsandals.bedwars.api.events.*;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.api.Team;

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
    public void onGameEnable(BedwarsGameEnabledEvent gameEnable) {
        Game game = gameEnable.getGame();
        List<Team> teams = game.getAvailableTeams();

        Bukkit.getLogger().info("Making cages wefkewjfbwe");
        for (Team t : teams) {
            Location teamSpawnLoc = t.getTeamSpawn();
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                buildCage(teamSpawnLoc);
            });
        }
    }

    @EventHandler
    public void onGameDisable(BedwarsGameDisabledEvent gameDisabled) {
        Game game = gameDisabled.getGame();
        List<Team> teams = game.getAvailableTeams();
        Bukkit.getLogger().info("Removing cages efkjbwekf");
        for (Team t : teams) {
            Location teamSpawnLoc = t.getTeamSpawn();
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                removeCage(teamSpawnLoc);
            });
        }
    }

    @EventHandler
    public void onGameRebuild(BedwarsGameChangedStatusEvent reBuilding) {
        Game game = reBuilding.getGame();
        List<Team> teams = game.getAvailableTeams();
        if (game.getStatus() == GameStatus.REBUILDING) {
            Bukkit.getLogger().info("Making cages dpfobjdrfo");
            for (Team t : teams) {
                Location teamSpawnLoc = t.getTeamSpawn();
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    buildCage(teamSpawnLoc);
                });
            }
        }
    }

    @EventHandler
    public void onGameStart(BedwarsGameStartedEvent gameStarted) {
        Game game = gameStarted.getGame();
        List<Team> teams = game.getAvailableTeams();
        Bukkit.getLogger().info("Removing cages kwkefhwui");
        for (Team t : teams) {
            Location teamSpawnLoc = t.getTeamSpawn();
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                removeCage(teamSpawnLoc);
            });
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

        for (int x = centerX - 2; x <= centerX + 2; x++) {
            for (int y = centerY - 2; y <= centerY + 2; y++) {
                for (int z = centerZ - 2; z <= centerZ + 2; z++) {
                    if (x == centerX - 2 || x == centerX + 2 ||
                            y == centerY - 2 || y == centerY + 2 ||
                            z == centerZ - 2 || z == centerZ + 2) {
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

        for (int x = centerX - 2; x <= centerX + 2; x++) {
            for (int y = centerY - 2; y <= centerY + 2; y++) {
                for (int z = centerZ - 2; z <= centerZ + 2; z++) {
                    if (x == centerX - 2 || x == centerX + 2 ||
                            y == centerY - 2 || y == centerY + 2 ||
                            z == centerZ - 2 || z == centerZ + 2) {
                        Location blockLoc = new Location(world, x, y, z);
                        world.getBlockAt(blockLoc).setType(Material.AIR);
                    }
                }
            }
        }
    }
}

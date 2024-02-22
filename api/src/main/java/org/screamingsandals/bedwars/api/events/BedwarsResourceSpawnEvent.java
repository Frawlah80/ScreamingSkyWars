/*
 * Copyright (C) 2023 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.api.events;

import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.ItemSpawner;
import org.screamingsandals.bedwars.api.game.ItemSpawnerType;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * @author Bedwars Team
 */
public class BedwarsResourceSpawnEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private Game game;
    private ItemStack resource;
    private ItemSpawner spawner;

    /**
     * @param game
     * @param spawner
     * @param resource
     */
    public BedwarsResourceSpawnEvent(Game game, ItemSpawner spawner, ItemStack resource) {
        this.game = game;
        this.spawner = spawner;
        this.resource = resource;
    }

    public static HandlerList getHandlerList() {
        return BedwarsResourceSpawnEvent.handlers;
    }

    /**
     * @return game
     */
    public Game getGame() {
        return this.game;
    }

    @Override
    public HandlerList getHandlers() {
        return BedwarsResourceSpawnEvent.handlers;
    }

    /**
     * @return spawner
     */
    public ItemSpawner getSpawner() {
        return this.spawner;
    }

    /**
     * @return location
     */
    public Location getLocation() {
        return this.spawner.getLocation();
    }

    /**
     * @return resource
     */
    public ItemStack getResource() {
        return this.resource;
    }

    /**
     * @param resource
     */
    public void setResource(ItemStack resource) {
        this.resource = resource;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /**
     * @return spawner type
     */
    public ItemSpawnerType getType() {
        return this.spawner.getItemSpawnerType();
    }

}

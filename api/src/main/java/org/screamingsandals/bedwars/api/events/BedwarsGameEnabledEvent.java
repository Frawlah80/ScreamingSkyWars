package org.screamingsandals.bedwars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.screamingsandals.bedwars.api.game.Game;

/**
 * @author Bedwars Team
 */
public class BedwarsGameEnabledEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Game game;

    /**
     * @param game
     */
    public BedwarsGameEnabledEvent(Game game) {
        this.game = game;
    }

    @Override
    public HandlerList getHandlers() {
        return BedwarsGameEnabledEvent.handlers;
    }

    /**
     * @return game
     */
    public Game getGame() {
        return this.game;
    }

    public static HandlerList getHandlerList() {
        return BedwarsGameEnabledEvent.handlers;
    }

}

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

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.game.Game;

/**
 * @author Bedwars Team
 *
 */
public class BedwarsGameEndingEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Game game;
	private RunningTeam winningTeam;

	/**
	 * @param game
	 */
	public BedwarsGameEndingEvent(Game game, @Nullable RunningTeam winningTeam) {
		this.winningTeam = winningTeam;
		this.game = game;
	}

	public static HandlerList getHandlerList() {
		return BedwarsGameEndingEvent.handlers;
	}

	/**
	 * @return game
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * @return team that won bedwars match
	 */
	public @Nullable RunningTeam getWinningTeam() {
		return this.winningTeam;
	}

	@Override
	public HandlerList getHandlers() {
		return BedwarsGameEndingEvent.handlers;
	}

}

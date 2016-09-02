/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Chris Martin (OmniCypher-), Dylan Curzon (curz46), Connor Hartley (connorhartley)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.abilityapi.trigger;

import com.github.abilityapi.trigger.sequence.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * A few predefined actions to help with sequence building.
 */
public interface Actions {

    /**
     * Fired on any click type.
     */
    Action<PlayerInteractEvent> CLICK = new Action<>(PlayerInteractEvent.class);

    /**
     * Fired on left click, specifically. Both air and block.
     */
    Action<PlayerInteractEvent> LEFT_CLICK = new Action<>(PlayerInteractEvent.class, (player, event) ->
        event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR
                || event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK);

    /**
     * Fired on right click, specifically. Both air and block.
     */
    Action<PlayerInteractEvent> RIGHT_CLICK = new Action<>(PlayerInteractEvent.class, (player, event) ->
        event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR
                || event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);

    /**
     * Fired on shift down (start sneaking).
     */
    Action<PlayerToggleSneakEvent> SHIFT_DOWN = new Action<>(PlayerToggleSneakEvent.class, (player, event) -> event.isSneaking());

    /**
     * Fired on shift up (stop sneaking).
     */
    Action<PlayerToggleSneakEvent> SHIFT_UP = new Action<>(PlayerToggleSneakEvent.class, (player, event) -> !event.isSneaking());

}

/*
 * MIT License
 *
 * Copyright (c) 2016 Chris Martin (OmniCypher-), Dylan Curzon (curz46), Connor Hartley (ConnorHartley)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.abilityapi.sequence.action;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public final class Actions {

    public static ActionBlueprint<PlayerInteractEvent> INTERACT = () ->
            new Action<>(PlayerInteractEvent.class);

    public static ActionBlueprint<PlayerInteractEvent> INTERACT_LEFT = () ->
            new Action<>(PlayerInteractEvent.class, (player, event) ->
                    event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR ||
                    event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK);

    public static ActionBlueprint<PlayerInteractEvent> INTERACT_RIGHT = () ->
            new Action<>(PlayerInteractEvent.class, (player, event) ->
                    event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR ||
                    event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);

    public static ActionBlueprint<PlayerToggleSneakEvent> TOGGLE_SNEAK = () ->
            new Action<>(PlayerToggleSneakEvent.class);

    public static ActionBlueprint<PlayerToggleSneakEvent> START_SNEAK = () ->
            new Action<>(PlayerToggleSneakEvent.class, (player, event) -> !player.isSneaking());

    public static ActionBlueprint<PlayerToggleSneakEvent> STOP_SNEAK = () ->
            new Action<>(PlayerToggleSneakEvent.class, (player, event) -> player.isSneaking());

}

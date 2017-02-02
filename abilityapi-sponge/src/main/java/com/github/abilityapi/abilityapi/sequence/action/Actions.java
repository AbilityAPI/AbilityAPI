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
package com.github.abilityapi.abilityapi.sequence.action;

import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;

public final class Actions {

    public static ActionBlueprint<InteractBlockEvent.Primary> INTERACT_BLOCK_PRIMARY = () ->
            new Action<>(InteractBlockEvent.Primary.class);

    public static ActionBlueprint<InteractBlockEvent.Secondary> INTERACT_BLOCK_SECONDARY = () ->
            new Action<>(InteractBlockEvent.Secondary.class);

    public static ActionBlueprint<InteractEntityEvent.Primary> INTERACT_ENTITY_PRIMARY = () ->
            new Action<>(InteractEntityEvent.Primary.class);

    public static ActionBlueprint<InteractEntityEvent.Secondary> INTERACT_ENTITY_SECONDARY = () ->
            new Action<>(InteractEntityEvent.Secondary.class);

    public static ActionBlueprint<InteractInventoryEvent.Open> OPEN_INVENTORY = () ->
            new Action<>(InteractInventoryEvent.Open.class);

    public static ActionBlueprint<InteractInventoryEvent.Close> CLOSE_INVENTORY = () ->
            new Action<>(InteractInventoryEvent.Close.class);

    public static ActionBlueprint<InteractItemEvent.Primary> INTERACT_ITEM_PRIMARY = () ->
            new Action<>(InteractItemEvent.Primary.class);

    public static ActionBlueprint<InteractItemEvent.Secondary> INTERACT_ITEM_SECONDARY = () ->
            new Action<>(InteractItemEvent.Secondary.class);

    public static ActionBlueprint<MoveEntityEvent> PLAYER_MOVE = () ->
            new Action<>(MoveEntityEvent.class);

}

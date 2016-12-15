package com.github.abilityapi.sequence.action;

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

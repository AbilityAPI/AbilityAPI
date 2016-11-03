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

package com.github.abilityapi.sequence;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class SequenceListener implements Listener {

    private final SequenceManager manager;

    public SequenceListener(SequenceManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onAnimationEvent(PlayerAnimationEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onChangedMainHand(PlayerChangedMainHandEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onChangedWorld(PlayerChangedWorldEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        manager.invoke(event.getEntity(), event);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onEditBook(PlayerEditBookEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onEntityLeash(PlayerLeashEntityEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onPickupArrow(PlayerPickupArrowEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onShearEntity(PlayerShearEntityEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onStatisticIncrement(PlayerStatisticIncrementEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onToggleSprint(PlayerToggleSprintEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onUnleashEntity(PlayerUnleashEntityEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    // block events

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    // inventory events

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        manager.invoke(event.getEnchanter(), event);
    }

    @EventHandler
    public void onPrepareEnchantItem(PrepareItemEnchantEvent event) {
        manager.invoke(event.getEnchanter(), event);
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent event) {
        manager.invoke((Player) event.getWhoClicked(), event);
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        manager.invoke((Player) event.getWhoClicked(), event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        manager.invoke((Player) event.getPlayer(), event);
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
        manager.invoke((Player) event.getWhoClicked(), event);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        manager.invoke((Player) event.getPlayer(), event);
    }

    // entity events

    @EventHandler
    public void onHeldBreathChange(EntityAirChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityDoorBreak(EntityBreakDoorEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onPortalCreation(EntityCreatePortalEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            manager.invoke((Player) event.getDamager(), event);
        }
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onPortalExit(EntityPortalExitEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onHeathRegain(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {
        if (event.getOwner() instanceof Player) {
            manager.invoke((Player) event.getOwner(), event);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            manager.invoke((Player) event.getTarget(), event);
        }
    }

    @EventHandler
    public void onTargetLivingEnitiy(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player) {
            manager.invoke((Player) event.getTarget(), event);
        }
    }

    @EventHandler
    public void onGlideToggle(EntityUnleashEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onFoodRegain(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            manager.invoke((Player) event.getEntity(), event);
        }
    }

    @EventHandler
    public void onHangingEntityBreak(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player) {
            manager.invoke((Player) event.getRemover(), event);
        }
    }

    @EventHandler
    public void onHangingEntityPlace(HangingPlaceEvent event) {
        manager.invoke(event.getPlayer(), event);
    }

    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent event) {
        if (event.getAttacker() instanceof Player) {
            manager.invoke((Player) event.getAttacker(), event);
        }
    }

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (event.getAttacker() instanceof Player) {
            manager.invoke((Player) event.getAttacker(), event);
        }
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            manager.invoke((Player) event.getEntered(), event);
        }
    }

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            manager.invoke((Player) event.getExited(), event);
        }
    }

}

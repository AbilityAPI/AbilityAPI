package com.github.abilityapi.events;

import com.github.abilityapi.ability.Ability;
import com.github.abilityapi.user.User;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class AbilityStartEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final User user;
    private final Ability ability;

    private boolean cancelled;

    public AbilityStartEvent(User user, Ability ability) {
        super(user.getPlayer());
        this.user = user;
        this.ability = ability;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public User getUser() {
        return user;
    }

    public Ability getAbility() {
        return ability;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

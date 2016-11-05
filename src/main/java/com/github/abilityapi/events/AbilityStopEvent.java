package com.github.abilityapi.events;

import com.github.abilityapi.ability.Ability;
import com.github.abilityapi.user.User;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class AbilityStopEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final User user;
    private final Ability ability;

    public AbilityStopEvent(User user, Ability ability) {
        super(user.getPlayer());
        this.user = user;
        this.ability = ability;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public User getUser() {
        return user;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

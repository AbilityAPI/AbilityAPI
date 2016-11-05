package com.github.abilityapi.events;

import com.github.abilityapi.sequence.Sequence;
import com.github.abilityapi.user.User;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SequenceBeginEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final User user;
    private final Sequence sequence;

    private boolean cancelled;

    public SequenceBeginEvent(User user, Sequence sequence) {
        super(user.getPlayer());
        this.user = user;
        this.sequence = sequence;
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

    public Sequence getSequence() {
        return sequence;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

package com.github.abilityapi.events;

import com.github.abilityapi.sequence.Sequence;
import com.github.abilityapi.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SequenceSucceedEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final User user;
    private final Sequence sequence;
    private final Event succeededEvent;

    private boolean cancelled;

    public SequenceSucceedEvent(User user, Sequence sequence, Event succeededEvent) {
        super(user.getPlayer());
        this.user = user;
        this.sequence = sequence;
        this.succeededEvent = succeededEvent;
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

    public Event getSucceededEvent() {
        return succeededEvent;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

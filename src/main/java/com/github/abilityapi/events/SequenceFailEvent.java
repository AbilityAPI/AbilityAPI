package com.github.abilityapi.events;

import com.github.abilityapi.sequence.Sequence;
import com.github.abilityapi.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SequenceFailEvent extends PlayerEvent {

    public enum SequenceFailReason {

        EVENT,
        DELAY,
        EXPIRE,
        CONDITION

    }

    private static final HandlerList handlers = new HandlerList();

    private final User user;
    private final Sequence sequence;
    private final Event failedEvent;
    private final SequenceFailReason reason;

    public SequenceFailEvent(User user, Sequence sequence, Event failedEvent, SequenceFailReason reason) {
        super(user.getPlayer());
        this.user = user;
        this.sequence = sequence;
        this.failedEvent = failedEvent;
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public User getUser() {
        return user;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Event getFailedEvent() {
        return failedEvent;
    }

    public SequenceFailReason getReason() {
        return reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

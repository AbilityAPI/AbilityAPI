package com.github.abilityapi.events;

import com.github.abilityapi.sequence.Sequence;
import com.github.abilityapi.user.User;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

public class SequenceFinishEvent extends AbstractEvent implements Cancellable {

    private final User user;
    private final Sequence sequence;
    private final Cause cause;
    private boolean cancelled;

    public SequenceFinishEvent(User user, Sequence sequence, Cause cause) {
        this.user = user;
        this.sequence = sequence;
        this.cause = cause;
    }

    public User getUser() {
        return this.user;
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    @Override
    public Cause getCause() {
        return this.cause;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}

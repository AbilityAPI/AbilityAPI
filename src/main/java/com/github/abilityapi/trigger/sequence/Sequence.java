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

package com.github.abilityapi.trigger.sequence;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Sequence {

    private List<Action> actions;

    private long last = System.currentTimeMillis();
    private boolean cancelled;

    public Sequence(List<Action> actions) {
        this.actions = actions;
    }

    /**
     * @return A new SequenceBuilder instance.
     * @see SequenceBuilder
     */
    public static SequenceBuilder builder() {
        return new SequenceBuilder();
    }

    /**
     * Test if all currently testable conditions are satisfied.
     */
    public <T extends Event> boolean next(Player player, T event) {
        Iterator<Action> it = actions.iterator();

        if (!it.hasNext()) {
            return true; // there are no more conditions, finished.
        }

        Action action = it.next();

        if (!checkEvent(action, event)) {
            if (!checkCancelledEvent(action, event)) {
                cancelled = true;
            }

            return false;
        }

        //noinspection unchecked
        Action<T> casted = (Action<T>) action;

        if (!checkDelay(casted)
                || !checkExpire(casted)
                || !checkConditions(casted, player, event)) {
            return false;
        }

        last = System.currentTimeMillis();
        actions.remove(casted);
        return true;
    }

    public boolean hasFinished() {
        return actions.isEmpty();
    }

    public boolean hasExpired() {
        if (hasFinished()) {
            return false; // if sequence has finished, it can't have expired.
        }

        Action action = actions.get(0);

        if (!action.getExpire().isPresent()) {
            return false; // never expires. never...
        }

        return System.currentTimeMillis() > last + (int) action.getExpire().get() * 1000;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    // the below return true if the condition is met.

    private boolean checkCancelledEvent(Action action, Event event) {
        return !action.getCancelEvents().contains(event.getClass());
    }

    private boolean checkEvent(Action action, Event event) {
        return action.getEventClass().equals(event.getClass());
    }

    private boolean checkDelay(Action action) {
        if (!action.getDelay().isPresent()) {
            return true;
        }

        long now = System.currentTimeMillis();
        long delay = (int) action.getDelay().get() * 1000;

        return now > last + delay;
    }

    private boolean checkExpire(Action action) {
        if (!action.getExpire().isPresent()) {
            return true;
        }

        long now = System.currentTimeMillis();
        long expiry = (int) action.getExpire().get() * 1000;

        return now < last + expiry;
    }

    private <T extends Event> boolean checkConditions(Action<T> action, Player player, T event) {
        Collection<Condition<T>> conditions = action.getConditions();
        boolean result = !conditions.stream()
                .filter(condition -> !condition.test(player, event))
                .findFirst()
                .isPresent();

        return result;
    }

}

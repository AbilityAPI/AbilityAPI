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
     * @return true or false, depending on if testable conditions were satisfied.
     */
    public <T extends Event> boolean next(Player player, T event) {
        Iterator<Action> it = actions.iterator();

        if (!it.hasNext()) {
            return true; // there are no more conditions, finished.
        }

        Action action = it.next();

        if (action.getCancelEvents().contains(event.getClass())) {
            cancelled = true;
            return false;
        }

        if (!action.getEventClass().equals(event.getClass())) {
            return false;
        }

        //noinspection unchecked
        Action<T> casted = (Action<T>) action;

        if (action.getDelay().isPresent()) {
            if (System.currentTimeMillis() < last + (int) action.getDelay().get() * 1000) {
                cancelled = true;
                return false; // condition failed because action delay hasn't completed.
            }
        }

        if (action.getExpire().isPresent()) {
            if (System.currentTimeMillis() > last + (int) action.getExpire().get() * 1000) {
                return false; // condition failed because action has expired.
            }
        }

        Collection<Condition<T>> conditions = casted.getConditions();
        boolean result = !conditions.stream()
                .filter(condition -> !condition.test(player, event))
                .findFirst()
                .isPresent();

        if (result) {
            last = System.currentTimeMillis();
            it.remove();
        }

        return result;
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

}

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

import com.github.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.sequence.action.Action;
import com.github.abilityapi.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sequence {

    private final User user;
    private final AbilityProvider provider;

    private final List<Action> actions = new ArrayList<>();
    private final List<Event> successfulEvents = new ArrayList<>();

    private long last = System.currentTimeMillis();

    private boolean cancelled;
    private boolean finished;

    public Sequence(User user, AbilityProvider provider, List<Action> actions) {
        this.user = user;
        this.provider = provider;
        this.actions.addAll(actions);
    }

    public <T extends Event> boolean pass(Player player, T event) {
        Iterator<Action> it = actions.iterator();

        if (it.hasNext()) {
            Action action = it.next();

            long now = System.currentTimeMillis();

            if (!action.getEvent().equals(event.getClass())
                    || last + ((action.getDelay() / 20) * 1000) > now
                    || last + ((action.getExpire() / 20) * 1000) < now) {
                cancelled = action.fail(player, event);
                return false;
            }

            //noinspection unchecked
            Action<T> tAction = (Action<T>) action;

            if (!tAction.testConditions(player, event)) {
                cancelled = action.fail(player, event);
                return false;
            }

            last = System.currentTimeMillis();
            successfulEvents.add(event);
            it.remove();
            tAction.succeed(player, event);

            if (!it.hasNext()) {
                finished = true;
                return true;
            }

            return true;
        }

        return true;
    }

    public boolean hasExpired() {
        if (actions.isEmpty()) {
            return false;
        }

        Action action = actions.get(0);
        long now = System.currentTimeMillis();

        return action != null && last + ((action.getExpire() / 20) * 1000) < now;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isFinished() {
        return finished;
    }

    public User getUser() {
        return user;
    }

    public AbilityProvider getProvider() {
        return provider;
    }

    public List<Event> getSuccessfulEvents() {
        return successfulEvents;
    }

}

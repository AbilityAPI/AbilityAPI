/*
 * MIT License
 *
 * Copyright (c) 2016 Chris Martin (OmniCypher-), Dylan Curzon (curz46), Connor Hartley (ConnorHartley)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.abilityapi.abilityapi.sequence;

import com.github.abilityapi.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.abilityapi.events.SequenceFailEvent;
import com.github.abilityapi.abilityapi.events.SequenceSucceedEvent;
import com.github.abilityapi.abilityapi.sequence.action.Action;
import com.github.abilityapi.abilityapi.user.User;
import org.bukkit.Bukkit;
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
        Iterator<Action> it = this.actions.iterator();

        if (it.hasNext()) {
            Action action = it.next();

            long now = System.currentTimeMillis();

            if (!action.getEvent().equals(event.getClass())) {
                return fail(player, event, action, SequenceFailEvent.SequenceFailReason.EVENT);
            }

            if (this.last + ((action.getDelay() / 20) * 1000) > now) {
                return fail(player, event, action, SequenceFailEvent.SequenceFailReason.DELAY);
            }

            if (this.last + ((action.getExpire() / 20) * 1000) < now) {
                return fail(player, event, action, SequenceFailEvent.SequenceFailReason.EXPIRE);
            }

            //noinspection unchecked
            Action<T> tAction = (Action<T>) action;

            if (!tAction.testConditions(player, event)) {
                return fail(player, event, tAction, SequenceFailEvent.SequenceFailReason.CONDITION);
            }

            User user = User.get(player);
            SequenceSucceedEvent attempt = new SequenceSucceedEvent(user, this, event);
            Bukkit.getPluginManager().callEvent(attempt);
            if (attempt.isCancelled()) {
                return false;
            }

            this.last = System.currentTimeMillis();
            this.successfulEvents.add(event);
            it.remove();
            tAction.succeed(player, event);

            if (!it.hasNext()) {
                this.finished = true;
                return true;
            }

            return true;
        }

        return true;
    }

    public boolean fail(Player player, Event event, Action action, SequenceFailEvent.SequenceFailReason reason) {
        this.cancelled = action.fail(player, event);

        User user = User.get(player);
        SequenceFailEvent failEvent = new SequenceFailEvent(user, this, event, reason);
        Bukkit.getPluginManager().callEvent(failEvent);

        return false;
    }

    public boolean hasExpired() {
        if (this.actions.isEmpty()) {
            return false;
        }

        Action action = this.actions.get(0);
        long now = System.currentTimeMillis();

        return action != null && this.last + ((action.getExpire() / 20) * 1000) < now;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public User getUser() {
        return this.user;
    }

    public AbilityProvider getProvider() {
        return this.provider;
    }

    public List<Event> getSuccessfulEvents() {
        return this.successfulEvents;
    }

}

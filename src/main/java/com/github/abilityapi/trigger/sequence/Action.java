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

import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class Action<T extends Event> {

    private final Class<T> eventClazz;
    private final Collection<Condition<T>> conditions = new ArrayList<>();
    private final Collection<Class<? extends Event>> cancelEvents = new ArrayList<>();
    private Optional<Integer> delay = Optional.empty();
    private Optional<Integer> expire = Optional.empty();

    public Action(Class<T> eventClazz) {
        this.eventClazz = eventClazz;
    }

    public Action(Class<T> eventClazz, Condition<T> initial) {
        this(eventClazz);
        this.conditions.add(initial);
    }

    /**
     * @return A new Action instance with the same values as this instance.
     */
    public Action<T> copy() {
        Action<T> action = new Action<>(eventClazz);

        action.conditions.addAll(conditions);
        action.cancelEvents.addAll(cancelEvents);
        action.delay = delay;
        action.expire = expire;

        return action;
    }

    public Class<T> getEventClass() {
        return eventClazz;
    }

    public Collection<Condition<T>> getConditions() {
        return conditions;
    }

    public Collection<Class<? extends Event>> getCancelEvents() {
        return cancelEvents;
    }

    public Optional<Integer> getDelay() {
        return delay;
    }

    public Optional<Integer> getExpire() {
        return expire;
    }

    public void setDelay(int delay) {
        this.delay = Optional.of(delay);
    }

    public void setExpire(int expire) {
        this.expire = Optional.of(expire);
    }

}

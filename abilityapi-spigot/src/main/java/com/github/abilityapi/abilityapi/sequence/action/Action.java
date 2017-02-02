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
package com.github.abilityapi.abilityapi.sequence.action;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Action<T extends Event> {

    private final List<Condition<T>> conditions = new ArrayList<>();
    private final List<Condition> successListeners = new ArrayList<>();
    private final List<Condition> failureListeners = new ArrayList<>();

    private final Class<T> event;

    private int delay;
    private int expire;

    public Action(Class<T> event) {
        this.event = event;
    }

    @SafeVarargs
    Action(Class<T> event, Condition<T>... conditions) {
        this(event);
        this.conditions.addAll(Arrays.asList(conditions));
    }

    public Action(Class<T> event, List<Condition<T>> conditions) {
        this(event);
        this.conditions.addAll(conditions);
    }

    void addCondition(Condition<T> condition) {
        this.conditions.add(condition);
    }

    void setDelay(int delay) {
        this.delay = delay;
    }

    void setExpire(int expire) {
        this.expire = expire;
    }

    public void succeed(Player player, Event event) {
        this.successListeners.forEach(runnable -> runnable.test(player, event));
    }

    public boolean fail(Player player, Event event) {
        // true if should cancel
        return this.failureListeners.stream()
                .filter(callback -> callback.test(player, event))
                .findFirst()
                .isPresent();
    }

    public boolean testConditions(Player player, T event) {
        // fails if one present
        return !this.conditions.stream()
                .filter(condition -> !condition.test(player, event)) // present if one fails
                .findFirst()
                .isPresent();
    }

    public Class<T> getEvent() {
        return this.event;
    }

    public List<Condition<T>> getConditions() {
        return this.conditions;
    }

    public int getDelay() {
        return this.delay;
    }

    public int getExpire() {
        return this.expire;
    }

    void onSuccess(Condition condition) {
        this.successListeners.add(condition);
    }

    void onFailure(Condition condition) {
        this.failureListeners.add(condition);
    }

}

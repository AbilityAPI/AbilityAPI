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

import java.util.Arrays;

public class ActionBuilder<T extends Event> {

    private final SequenceBuilder builder;
    private final Action<T> action;

    public ActionBuilder(SequenceBuilder builder, Action<T> action) {
        this.builder = builder;
        this.action = action;
    }

    /**
     * Add a Condition to this Action.
     *
     * @return This ActionBuilder instance.
     */
    public ActionBuilder<T> condition(Condition<T> condition) {
        action.getConditions().add(condition);
        return this;
    }

    /**
     * Add an Action (created by class) to the underlying SequenceBuilder.
     *
     * @param other Event class to create Action of.
     * @return A new ActionBuilder instance for the Action created.
     */
    public <K extends Event> ActionBuilder<K> action(Class<K> other) {
        return builder.action(other);
    }

    /**
     * Add an Action to the underlying SequenceBuilder.
     *
     * @return A new ActionBuilder instance for the Action added.
     */
    public <K extends Event> ActionBuilder<K> action(Action<K> other) {
        return builder.action(other);
    }

    /**
     * Add a list of Action event classes which will cancel the Sequence to be created if this specific Action receives
     * the event type while awaiting test.
     *
     * @return This ActionBuilder instance.
     */
    public ActionBuilder<T> cancel(Class<? extends Event>... events) {
        action.getCancelEvents().addAll(Arrays.asList(events));
        return this;
    }

    /**
     * Add a list of Action event classes (taken from the Action instances list provided) which will cancel the Sequence
     * to be created if this specific Action receives the event type while awaiting test.
     *
     * @return This ActionBuilder instance.
     */
    public ActionBuilder<T> cancel(Action<?>... actions) {
        Arrays.asList(actions).forEach(action -> {
            Class<? extends Event> clazz = action.getEventClass();
            System.out.println(clazz.getName() + " added to cancels");
            this.action.getCancelEvents().add(clazz);
        });
        return this;
    }

    /**
     * Set the delay of this Action.
     *
     * @return This ActionBuilder instance.
     */
    public ActionBuilder<T> delay(int value) {
        action.setDelay(value);
        return this;
    }

    /**
     * Set the expiry of this Action.
     *
     * @return This ActionBuilder instance.
     */
    public ActionBuilder<T> expire(int value) {
        action.setExpire(value);
        return this;
    }

    /**
     * Run SequenceBuilder#build.
     *
     * @return The created Sequence.
     * @see SequenceBuilder
     * @see Sequence
     */
    public Sequence build() {
        return builder.build();
    }

}

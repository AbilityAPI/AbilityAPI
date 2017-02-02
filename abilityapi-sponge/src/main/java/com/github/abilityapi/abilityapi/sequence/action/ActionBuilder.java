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

import com.github.abilityapi.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.abilityapi.sequence.SequenceBlueprint;
import com.github.abilityapi.abilityapi.sequence.SequenceBuilder;
import org.spongepowered.api.event.Event;

public class ActionBuilder<T extends Event> {

    private final SequenceBuilder builder;
    private final Action action;

    public ActionBuilder(SequenceBuilder builder, Action<T> action) {
        this.builder = builder;
        this.action = action;
    }

    public ActionBuilder<T> condition(Condition<T> condition) {
        this.action.addCondition(condition);
        return this;
    }

    public ActionBuilder<T> delay(int delay) {
        this.action.setDelay(delay);
        return this;
    }

    public ActionBuilder<T> expire(int expire) {
        this.action.setExpire(expire);
        return this;
    }

    public ActionBuilder<T> success(Condition condition) {
        this.action.onSuccess(condition);
        return this;
    }

    public ActionBuilder<T> failure(Condition condition) {
        this.action.onFailure(condition);
        return this;
    }

    public <T extends Event> ActionBuilder<T> action(Class<T> clazz) {
        return action(new Action<>(clazz));
    }

    public <T extends Event> ActionBuilder<T> action(ActionBlueprint<T> builder) {
        return action(builder.create());
    }

    public <K extends Event> ActionBuilder<K> action(Action<K> other) {
        return this.builder.action(other);
    }

    public SequenceBlueprint build(AbilityProvider provider) {
        return this.builder.build(provider);
    }

}

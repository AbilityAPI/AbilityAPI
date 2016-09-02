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
import org.bukkit.event.player.PlayerEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceBuilder {

    private List<Action> actions = new ArrayList<>();
    private Action current;

    /**
     * Add the initial Action (created by class) of the Sequence being created.
     *
     * @return A new ActionBuilder for the created Action.
     */
    public <T extends Event> ActionBuilder<T> action(Class<T> clazz) {
        current = new Action<>(clazz);
        actions.add(current);

        //noinspection unchecked
        return new ActionBuilder<>(this, current);
    }

    /**
     * Add the initial Action of the Sequence being created.
     *
     * @return A new ActionBuilder for the created Action.
     */
    public <T extends Event> ActionBuilder<T> action(Action<T> action) {
        current = action.copy();
        actions.add(current);

        //noinspection unchecked
        return new ActionBuilder<>(this, current);
    }

    /**
     * Build a new Sequence with the generated Actions list.
     */
    public Sequence build() {
        boolean noExpire = actions.stream()
                .skip(1)
                .filter(action -> !action.getExpire().isPresent())
                .findFirst()
                .isPresent();

        if (noExpire) {
            throw new RuntimeException("All actions (apart from the first) must have an expire!");
        }

        return new Sequence(actions);
    }

}

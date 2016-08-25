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

import com.github.abilityapi.trigger.ActionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceBuilder {

    private List<Action> actions = new ArrayList<>();
    private Action current;

    public SequenceBuilder condition(Condition condition) {
        if (current == null) {
            throw new RuntimeException("A condition must be applied to an action.");
        }

        current.getConditions().add(condition);
        return this;
    }

    public SequenceBuilder action(ActionType type) {
        current = new Action(type);
        actions.add(current);

        return this;
    }

    public SequenceBuilder cancel(ActionType... types) {
        if (current == null) {
            throw new RuntimeException("A delay must be applied to an action.");
        }

        current.getCancelTypes().addAll(Arrays.asList(types));
        return this;
    }

    public SequenceBuilder delay(int value) {
        if (current == null) {
            throw new RuntimeException("A delay must be applied to an action.");
        }

        if (actions.size() == 1) {
            throw new RuntimeException("The first action cannot have a delay.");
        }

        current.setDelay(value);
        return this;
    }

    public SequenceBuilder expire(int value) {
        if (current == null) {
            throw new RuntimeException("An expiration must be applied to an action.");
        }

        current.setExpire(value);
        return this;
    }

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

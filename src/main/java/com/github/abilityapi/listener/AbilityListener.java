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

package com.github.abilityapi.listener;

import com.github.abilityapi.Ability;
import com.github.abilityapi.trigger.Trigger;

public class AbilityListener {

    private final Ability ability;
    private final Trigger trigger;

    private ListenerRunnable runnableRun;
    private ListenerRunnable runnableOnce;

    private boolean onceDisabled;
    private boolean cancelled;

    public AbilityListener(Ability ability, Trigger trigger) {
        this.ability = ability;
        this.trigger = trigger;
    }

    public AbilityListener run(ListenerRunnable runnable) {
        this.runnableRun = runnable;
        return this;
    }

    public AbilityListener once(ListenerRunnable runnable) {
        this.runnableOnce = runnable;
        return this;
    }

    public void execute() {
        // if runnable exists AND runnable returned "cancel"
        if (runnableRun != null && runnableRun.run()) {
            cancel();
            return;
        }

        // ...
        if (runnableOnce != null && !onceDisabled) {
            onceDisabled = true;

            if (runnableOnce.run()) {
                cancel();
            }
        }
    }

    public Ability getAbility() {
        return ability;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    private void cancel() {
        cancelled = true;
    }

}

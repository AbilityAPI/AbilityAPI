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

/**
 * A class that allows Abilities to register Triggers to Runnables after the initial Trigger.
 * @see Ability
 * @see Trigger
 * @see ListenerRunnable
 */
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

    /**
     * Set the "run" runnable of this listener, to be executed every time the Trigger completes.
     * @see ListenerRunnable
     * @see Trigger
     *
     * @param runnable The ListenerRunnable instance to run.
     * @return This class instance for chain-building.
     */
    public AbilityListener run(ListenerRunnable runnable) {
        this.runnableRun = runnable;
        return this;
    }

    /**
     * Set the "once" runnable of this listener, to be executed ONCE when the Trigger completes. If the Trigger
     * completes a second or third (or fourth...) time, it will be ignored.
     * @see ListenerRunnable
     * @see Trigger
     *
     * @param runnable The ListenerRunnable instance to run.
     * @return This class instance for chain-building.
     */
    public AbilityListener once(ListenerRunnable runnable) {
        this.runnableOnce = runnable;
        return this;
    }

    /**
     * Attempt to run both the "run" and "once" runnables of this class. Intended for internal use.
     */
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

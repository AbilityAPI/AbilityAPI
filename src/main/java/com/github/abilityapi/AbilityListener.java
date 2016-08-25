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

package com.github.abilityapi;

import com.github.abilityapi.trigger.Trigger;
import com.github.abilityapi.trigger.TriggerManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AbilityListener {

    private final TriggerManager triggerManager;
    private final Trigger trigger;

    private Runnable runnableRun;
    private Runnable runnableOnce;
    private Runnable runnableUpdate;
    private BukkitTask task;
    private boolean disabled;

    public AbilityListener(TriggerManager triggerManager, Trigger trigger) {
        this.triggerManager = triggerManager;
        this.trigger = trigger;
    }

    public AbilityListener run(Runnable runnable) {
        if (this.runnableRun != null) {
            throw new RuntimeException("'run' already set.");
        }

        this.runnableRun = runnable;
        return this;
    }

    public AbilityListener once(Runnable runnable) {
        if (this.runnableOnce != null) {
            throw new RuntimeException("'once' already set.");
        }

        this.runnableOnce = runnable;
        return this;
    }

    public AbilityListener update(Runnable runnable) {
        if (this.runnableUpdate != null) {
            throw new RuntimeException("'update' already set.");
        }

        this.runnableUpdate = runnable;
        return this;
    }

    public void execute(JavaPlugin plugin) {
        if (runnableRun != null) {
            runnableRun.run();
        }

        if (runnableOnce != null && !disabled) {
            runnableOnce.run();
            disabled = true;
        }

        if (runnableUpdate != null) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    runnableUpdate.run();
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }

    public void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        triggerManager.removeListener(this);
    }

    public Trigger getTrigger() {
        return trigger;
    }

}

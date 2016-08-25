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

import com.github.abilityapi.trigger.TriggerListener;
import com.github.abilityapi.trigger.TriggerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityService implements Service {

    private final JavaPlugin plugin;
    private final AbilityManager abilityManager;
    private final TriggerManager triggerManager;

    public AbilityService(JavaPlugin plugin, AbilityManager abilityManager, TriggerManager triggerManager) {
        this.plugin = plugin;
        this.abilityManager = abilityManager;
        this.triggerManager = triggerManager;
    }

    @Override
    public void start() {
        Bukkit.getPluginManager().registerEvents(new TriggerListener(triggerManager), plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                triggerManager.checkExpire();

                abilityManager.updateAll();
                abilityManager.checkExpire();
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void stop() {}

}

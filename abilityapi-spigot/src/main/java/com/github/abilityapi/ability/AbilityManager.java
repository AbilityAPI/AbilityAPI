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

package com.github.abilityapi.ability;

import com.github.abilityapi.events.AbilityStartEvent;
import com.github.abilityapi.events.AbilityStopEvent;
import com.github.abilityapi.sequence.Sequence;
import com.github.abilityapi.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AbilityManager {

    private final List<Ability> abilities = new CopyOnWriteArrayList<>();
    private final JavaPlugin plugin;

    public AbilityManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(User user, Sequence sequence, AbilityProvider provider) {
        Ability ability = provider.createInstance(this, sequence, user);

        AbilityStartEvent attempt = new AbilityStartEvent(user, ability);
        Bukkit.getPluginManager().callEvent(attempt);
        if (attempt.isCancelled()) {
            return;
        }

        abilities.add(ability);
        user.getAbilities().add(ability);

        Bukkit.getPluginManager().registerEvents(ability, plugin);
        ability.start();
    }

    public void updateAll() {
        abilities.forEach(Ability::update);
    }

    public void cleanup() {
        abilities.removeIf(ability -> {
            if (ability.isExecuting()) {
                return false;
            }

            HandlerList.unregisterAll(ability);
            ability.stop();
            return true;
        });
    }

    public void stop(Ability ability) {
        User user = ability.getUser();

        AbilityStopEvent attempt = new AbilityStopEvent(user, ability);
        Bukkit.getPluginManager().callEvent(attempt);

        HandlerList.unregisterAll(ability);
        ability.stop();

        abilities.remove(ability);
        user.getAbilities().remove(ability);
    }

}

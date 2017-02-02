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
package com.github.abilityapi.abilityapi.ability;

import com.github.abilityapi.abilityapi.events.AbilityStartEvent;
import com.github.abilityapi.abilityapi.events.AbilityStopEvent;
import com.github.abilityapi.abilityapi.sequence.Sequence;
import com.github.abilityapi.abilityapi.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AbilityManager {

    private final List<Ability> abilities = new CopyOnWriteArrayList<>();
    private final Object plugin;

    public AbilityManager(Object plugin) {
        this.plugin = plugin;
    }

    public void execute(User user, Sequence sequence, AbilityProvider provider) {
        Ability ability = provider.createInstance(this, sequence, user);

        AbilityStartEvent attempt = new AbilityStartEvent(user, ability, Cause.builder().named(NamedCause.SOURCE, this.plugin).build());
        Sponge.getEventManager().post(attempt);
        if (attempt.isCancelled()) {
            return;
        }

        this.abilities.add(ability);
        user.getAbilities().add(ability);

        Sponge.getEventManager().registerListeners(this.plugin, ability);
        ability.start();
    }

    public void updateAll() {
        this.abilities.forEach(Ability::update);
    }

    public void cleanup() {
        this.abilities.removeIf(ability -> {
            if (ability.isExecuting()) {
                return false;
            }

            Sponge.getEventManager().unregisterListeners(ability);
            ability.stop();
            return true;
        });
    }

    public void stop(Ability ability) {
        User user = ability.getUser();

        AbilityStopEvent attempt = new AbilityStopEvent(user, ability, Cause.builder().named(NamedCause.SOURCE, this.plugin).build());
        Sponge.getEventManager().post(attempt);

        Sponge.getEventManager().unregisterListeners(ability);
        ability.stop();

        this.abilities.remove(ability);
        user.getAbilities().remove(ability);
    }

}

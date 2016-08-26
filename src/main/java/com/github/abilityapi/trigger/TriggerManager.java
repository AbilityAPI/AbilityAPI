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

package com.github.abilityapi.trigger;

import com.github.abilityapi.Ability;
import com.github.abilityapi.AbilityManager;
import com.github.abilityapi.AbilityProvider;
import com.github.abilityapi.AbilityRegistry;
import com.github.abilityapi.listener.AbilityListener;
import com.github.abilityapi.trigger.sequence.Sequence;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TriggerManager {

    private final JavaPlugin plugin;
    private final AbilityRegistry registry;
    private final AbilityManager abilityManager;

    private final Map<AbilityProvider, Sequence> abilityPotentials = new ConcurrentHashMap<>();
    private final Map<AbilityListener, Sequence> listenerPotentials = new ConcurrentHashMap<>();

    private final Map<Player, Ability> abilities;

    public TriggerManager(JavaPlugin plugin, AbilityRegistry registry, AbilityManager abilityManager) {
        this.plugin = plugin;
        this.registry = registry;
        this.abilityManager = abilityManager;

        this.abilities = abilityManager.getExecuting();
    }

    public <T extends PlayerEvent> void handle(T event, ActionType type) {
        // handle current potentials
        {
            Iterator<Map.Entry<AbilityProvider, Sequence>> it = abilityPotentials.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<AbilityProvider, Sequence> entry = it.next();
                Sequence sequence = entry.getValue();

                if (sequence.hasExpired()) {
                    it.remove();
                    continue;
                }

                sequence.next(event.getPlayer(), type);
                if (sequence.hasFinished()) {
                    abilityManager.execute(event.getPlayer(), entry.getKey());
                    it.remove();
                }
            }
        }

        {
            Iterator<Map.Entry<AbilityListener, Sequence>> it = listenerPotentials.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<AbilityListener, Sequence> entry = it.next();
                Sequence sequence = entry.getValue();

                if (sequence.hasExpired()) {
                    it.remove();
                    continue;
                }

                sequence.next(event.getPlayer(), type);
                if (sequence.hasFinished()) {
                    entry.getKey().execute();
                    it.remove();
                }
            }
        }

        // get new potentials
        for (Ability ability : abilities.values()) {
            for (AbilityListener listener : ability.getListeners()) {
                Trigger trigger = listener.getTrigger();
                Sequence sequence = trigger.createSequence();

                if (sequence.next(event.getPlayer(), type)) {
                    if (sequence.hasFinished()) {
                        listener.execute();
                        continue;
                    }

                    listenerPotentials.put(listener, sequence);
                }
            }
        }

        for (AbilityProvider provider : registry.getProviders()) {
            Trigger trigger = provider.getTrigger();
            Sequence sequence = trigger.createSequence();

            if (sequence.next(event.getPlayer(), type)) {
                if (sequence.hasFinished()) {
                    abilityManager.execute(event.getPlayer(), provider);
                    continue;
                }

                abilityPotentials.put(provider, sequence);
            }
        }
    }

    public void checkExpire() {
        {
            Iterator<Sequence> it = abilityPotentials.values().iterator();
            while (it.hasNext()) {
                if (it.next().hasExpired()) {
                    it.remove();
                }
            }
        }

        {
            Iterator<Map.Entry<AbilityListener, Sequence>> it = listenerPotentials.entrySet().iterator();
            while (it.hasNext()) {
                Ability ability = it.next().getKey().getAbility();
                if (!abilities.containsValue(ability)) {
                    it.remove();
                }
            }
        }
    }

}

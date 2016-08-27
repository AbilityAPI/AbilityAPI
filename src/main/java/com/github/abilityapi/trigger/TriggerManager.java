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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public <T extends PlayerEvent> void handle(T event) {
        Player player = event.getPlayer();

        {
            // handle current potentials for ability listeners
            Iterator<Map.Entry<AbilityListener, Sequence>> it = listenerPotentials.entrySet().iterator();
            handleCurrent(player, event, it).forEach(AbilityListener::execute);
        }

        {
            // handle current potentials for abilities
            Iterator<Map.Entry<AbilityProvider, Sequence>> it = abilityPotentials.entrySet().iterator();
            handleCurrent(player, event, it).forEach(provider -> abilityManager.execute(player, provider));
        }


        {
            // handle new potential triggers for ability listeners
            Iterator<Map.Entry<AbilityListener, Sequence>> it = abilities.values().stream()
                    .flatMap(ability -> ability.getListeners().stream())
                    .collect(Collectors.toMap(listener -> listener, listener -> listener.getTrigger().createSequence()))
                    .entrySet()
                    .iterator();
            handleNew(player, event, it, listenerPotentials)
                    .forEach(AbilityListener::execute);
        }

        {
            // handle new potential triggers for abilities
            Iterator<Map.Entry<AbilityProvider, Sequence>> it = registry.getProviders().stream()
                    .collect(Collectors.toMap(provider -> provider, provider -> provider.getTrigger().createSequence()))
                    .entrySet()
                    .iterator();
            handleNew(player, event, it, abilityPotentials)
                    .forEach(provider -> abilityManager.execute(player, provider));
        }
    }

    public void checkExpire() {
        {
            Iterator<Sequence> it = abilityPotentials.values().iterator();
            while (it.hasNext()) {
                Sequence sequence = it.next();
                if (sequence.isCancelled() || sequence.hasExpired()) {
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

    private <T> List<T> handleCurrent(Player player, PlayerEvent event, Iterator<Map.Entry<T, Sequence>> it) {
        List<T> finished = new ArrayList<>();

        while (it.hasNext()) {
            Map.Entry<T, Sequence> entry = it.next();
            T key = entry.getKey();
            Sequence sequence = entry.getValue();

            if (sequence.isCancelled() || sequence.hasExpired()) {
                it.remove();
                continue;
            }

            if (sequence.next(player, event) && sequence.hasFinished()) {
                finished.add(key);
                it.remove();
            }
        }

        return finished;
    }

    private <T> List<T> handleNew(Player player, PlayerEvent event, Iterator<Map.Entry<T, Sequence>> it,
                                      Map<T, Sequence> potentials) {
        List<T> finished = new ArrayList<>();

        while (it.hasNext()) {
            Map.Entry<T, Sequence> entry = it.next();
            T key = entry.getKey();
            Sequence sequence = entry.getValue();

            if (sequence.next(player, event)) {
                if (sequence.hasFinished()) {
                    finished.add(key);
                    continue;
                }

                potentials.put(key, sequence);
            }
        }

        return finished;
    }

}

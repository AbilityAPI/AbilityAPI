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
package com.github.abilityapi.abilityapi;

import com.github.abilityapi.abilityapi.ability.AbilityManager;
import com.github.abilityapi.abilityapi.external.Metrics;
import com.github.abilityapi.abilityapi.sequence.SequenceManager;
import com.github.abilityapi.abilityapi.services.AbilityService;
import com.github.abilityapi.abilityapi.services.SequenceService;
import com.github.abilityapi.abilityapi.services.UserService;
import com.google.inject.Inject;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "abilityapi",
        name = "AbilityAPI",
        version = "0.3",
        description = "An action and condition based Ability system.",
        authors = {
                "Chris Martin (OmniCypher-)",
                "Dylan Curzon (curz46)",
                "Connor Hartley (ConnorHartley)"
        }
)
public class AbilityAPI {

    private static AbilityAPI instance;

    @Inject
    private Metrics metrics;

    @Inject
    private PluginContainer pluginContainer;

    public PluginContainer getPluginContainer() {
        return this.pluginContainer;
    }

    private final AbilityManager abilityManager = new AbilityManager(this);
    private final SequenceManager sequenceManager = new SequenceManager(abilityManager, this);

    private final AbilityService abilityService = new AbilityService(this, abilityManager);
    private final SequenceService sequenceService = new SequenceService(this, sequenceManager);
    private final UserService userService = new UserService(this);

    private final List<Service> services = new ArrayList<>();

    public static AbilityAPI get() {
        return instance;
    }

    @Listener
    public void onServerStart(GameStartingServerEvent event) {
        instance = this;

        this.services.add(this.abilityService);
        this.services.add(this.sequenceService);
        this.services.add(this.userService);
        this.services.forEach(Service::start);
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        this.services.forEach(Service::stop);
        instance = null;
    }

}

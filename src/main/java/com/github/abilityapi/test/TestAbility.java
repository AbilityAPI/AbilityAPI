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

package com.github.abilityapi.test;

import com.github.abilityapi.Ability;
import com.github.abilityapi.AbilityAPI;
import com.github.abilityapi.trigger.ActionType;
import com.github.abilityapi.trigger.TriggerManager;
import com.github.abilityapi.trigger.sequence.Sequence;
import org.bukkit.entity.Player;

public class TestAbility extends Ability {

    protected final Player player;
    protected int ticks;

    public TestAbility(Player player) {
        this.player = player;

        AbilityAPI abilityAPI = AbilityAPI.get();
        TriggerManager manager = abilityAPI.getTriggerManager();

        manager.addListener(() -> Sequence.builder()
                .action(ActionType.CLICK)
                .build())
            .once(() -> player.sendMessage("Lemoncake!"));
    }

    @Override
    public long getExpireTicks() {
        return 200;
    }

    @Override
    public void start() {
        System.out.println("Ability - started!");
    }

    @Override
    public void update() {
        System.out.println("Ability - executed: " + ticks++);
    }

    @Override
    public void stop() {
        System.out.println("Ability - stopped!");
    }

}

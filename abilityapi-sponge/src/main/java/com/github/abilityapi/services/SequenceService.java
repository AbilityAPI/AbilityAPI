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
package com.github.abilityapi.services;

import com.github.abilityapi.Service;
import com.github.abilityapi.sequence.SequenceManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

public class SequenceService implements Service {

    private final Object plugin;
    private final SequenceManager sequenceManager;
//    private final SequenceListener listener;
    private Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
    private Task task;

    public SequenceService(Object plugin, SequenceManager sequenceManager) {
        this.plugin = plugin;
        this.sequenceManager = sequenceManager;
//        this.listener = new SequenceListener(sequenceManager);
    }

    @Override
    public void start() {
//        Sponge.getEventManager().registerListeners(this.plugin, this.listener);

        this.task = taskBuilder.execute(this.sequenceManager::cleanup).intervalTicks(1).submit(this.plugin);
    }

    @Override
    public void stop() {
//        Sponge.getEventManager().unregisterListeners(this.listener);
    }

}

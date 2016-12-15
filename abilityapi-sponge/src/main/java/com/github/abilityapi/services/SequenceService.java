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

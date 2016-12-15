package com.github.abilityapi.services;

import com.github.abilityapi.Service;
import com.github.abilityapi.ability.AbilityManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

public class AbilityService implements Service {

    private Object plugin;
    private AbilityManager abilityManager;

    private Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
    private Task task;

    public AbilityService(Object plugin, AbilityManager abilityManager) {
        this.plugin = plugin;
        this.abilityManager = abilityManager;
    }

    @Override
    public void start() {
        this.task = taskBuilder.execute(() -> {
            abilityManager.cleanup();
            abilityManager.updateAll();
        }).intervalTicks(1).submit(plugin);
    }

    @Override
    public void stop() {
        this.task.cancel();
    }

}

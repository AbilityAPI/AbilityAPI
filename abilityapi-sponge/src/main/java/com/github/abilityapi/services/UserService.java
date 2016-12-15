package com.github.abilityapi.services;

import com.github.abilityapi.Service;
import com.github.abilityapi.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;

public class UserService implements Service {

    private final Object plugin;

    public UserService(Object plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {
        Sponge.getEventManager().registerListeners(this.plugin, this);
    }

    @Override
    public void stop() {
        Sponge.getEventManager().unregisterListeners(this);
    }

    @Listener
    public void onPlayerQuit(TargetPlayerEvent event, @First Player player) {
        if(!player.isOnline()) {
            User.obliterate(player);
        }
    }

}

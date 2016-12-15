package com.github.abilityapi.sequence;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;

public interface SequenceInvoker {

    void invoke(Player player, Event event);

}

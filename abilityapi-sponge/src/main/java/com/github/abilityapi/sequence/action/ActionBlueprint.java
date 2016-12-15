package com.github.abilityapi.sequence.action;

import org.spongepowered.api.event.Event;

public interface ActionBlueprint<T extends Event> {

    Action<T> create();

}

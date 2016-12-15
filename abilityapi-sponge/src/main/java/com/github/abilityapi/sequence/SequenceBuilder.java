package com.github.abilityapi.sequence;

import com.github.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.sequence.action.Action;
import com.github.abilityapi.sequence.action.ActionBlueprint;
import com.github.abilityapi.sequence.action.ActionBuilder;
import com.github.abilityapi.user.User;
import org.spongepowered.api.event.Event;

import java.util.ArrayList;
import java.util.List;

public class SequenceBuilder {

    private List<Action> actions = new ArrayList<>();

    public <T extends Event> ActionBuilder<T> action(Class<T> clazz) {
        return action(new Action<>(clazz));
    }

    public <T extends Event> ActionBuilder<T> action(ActionBlueprint<T> builder) {
        return action(builder.create());
    }

    public <T extends Event> ActionBuilder<T> action(Action<T> action) {
        this.actions.add(action);

        return new ActionBuilder<>(this, action);
    }

    public SequenceBlueprint build(AbilityProvider provider) {
        return new SequenceBlueprint(provider) {
            @Override
            public Sequence create(User user) {
                return new Sequence(user, provider, actions);
            }
        };
    }

}

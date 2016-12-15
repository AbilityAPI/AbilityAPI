package com.github.abilityapi.sequence.action;

import com.github.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.sequence.SequenceBlueprint;
import com.github.abilityapi.sequence.SequenceBuilder;
import org.spongepowered.api.event.Event;

public class ActionBuilder<T extends Event> {

    private final SequenceBuilder builder;
    private final Action action;

    public ActionBuilder(SequenceBuilder builder, Action<T> action) {
        this.builder = builder;
        this.action = action;
    }

    public ActionBuilder<T> condition(Condition<T> condition) {
        this.action.addCondition(condition);
        return this;
    }

    public ActionBuilder<T> delay(int delay) {
        this.action.setDelay(delay);
        return this;
    }

    public ActionBuilder<T> expire(int expire) {
        this.action.setExpire(expire);
        return this;
    }

    public ActionBuilder<T> success(Condition condition) {
        this.action.onSuccess(condition);
        return this;
    }

    public ActionBuilder<T> failure(Condition condition) {
        this.action.onFailure(condition);
        return this;
    }

    public <T extends Event> ActionBuilder<T> action(Class<T> clazz) {
        return action(new Action<>(clazz));
    }

    public <T extends Event> ActionBuilder<T> action(ActionBlueprint<T> builder) {
        return action(builder.create());
    }

    public <K extends Event> ActionBuilder<K> action(Action<K> other) {
        return this.builder.action(other);
    }

    public SequenceBlueprint build(AbilityProvider provider) {
        return this.builder.build(provider);
    }

}

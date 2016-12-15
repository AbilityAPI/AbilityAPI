package com.github.abilityapi.sequence;

import com.github.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.user.User;

public abstract class SequenceBlueprint {

    protected final AbilityProvider provider;

    protected SequenceBlueprint(AbilityProvider provider) {
        this.provider = provider;
    }

    public abstract Sequence create(User user);

    public AbilityProvider getProvider() {
        return this.provider;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Sequence) {
            return ((Sequence) object).getProvider().equals(this.provider);
        } else if (object instanceof SequenceBlueprint) {
            return ((SequenceBlueprint) object).getProvider().equals(this.provider);
        }

        return false;
    }

}

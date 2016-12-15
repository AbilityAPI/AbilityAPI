package com.github.abilityapi.ability;

public interface AbilityRegistry {

    void register(AbilityProvider provider);

    void unregister(AbilityProvider provider);

    void unregister(Class<? extends AbilityProvider> providerClass);

}


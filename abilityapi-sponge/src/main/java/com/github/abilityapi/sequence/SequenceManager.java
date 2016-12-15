package com.github.abilityapi.sequence;

import com.github.abilityapi.ability.AbilityManager;
import com.github.abilityapi.ability.AbilityProvider;
import com.github.abilityapi.ability.AbilityRegistry;
import com.github.abilityapi.events.SequenceBeginEvent;
import com.github.abilityapi.events.SequenceFinishEvent;
import com.github.abilityapi.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

import java.util.ArrayList;
import java.util.List;

public class SequenceManager implements SequenceInvoker, AbilityRegistry {

    private final Object plugin;
    private final AbilityManager abilityManager;
    private final List<SequenceBlueprint> blueprints = new ArrayList<>();

    public SequenceManager(AbilityManager abilityManager, Object plugin) {
        this.plugin = plugin;
        this.abilityManager = abilityManager;
    }

    public void invoke(Player player, Event event) {
        User user = User.get(player);
        SequenceHandle handle = user.getSequenceHandle();

        // 1. find currently executing sequences, pass the event, check result

        List<Sequence> currentlyExecuting = handle.getExecuting();

        currentlyExecuting.forEach(sequence -> sequence.pass(player, event));
        currentlyExecuting.removeIf(Sequence::isCancelled);
        currentlyExecuting.removeIf(Sequence::hasExpired);
        currentlyExecuting.removeIf(sequence -> {
            if (!sequence.isFinished()) {
                return false;
            }

            SequenceFinishEvent attempt = new SequenceFinishEvent(user, sequence, Cause.builder().named(NamedCause.SOURCE, this.plugin).build());
            Sponge.getEventManager().post(attempt);
            if (attempt.isCancelled()) {
                return true;
            }

            AbilityProvider provider = sequence.getProvider();
            this.abilityManager.execute(user, sequence, provider);
            return true;
        });

        // 2. create new instance of every sequence, pass the event, check result

        this.blueprints.stream()
                .filter(blueprint -> !currentlyExecuting.contains(blueprint))
                .forEach(blueprint -> {
                    Sequence sequence = blueprint.create(user);

                    SequenceBeginEvent attempt = new SequenceBeginEvent(user, sequence, Cause.builder().named(NamedCause.SOURCE, this.plugin).build());
                    Sponge.getEventManager().post(attempt);
                    if (attempt.isCancelled()) {
                        return;
                    }

                    if (sequence.pass(player, event)) {
                        if (sequence.isCancelled()) {
                            return;
                        }

                        if (sequence.isFinished()) {
                            AbilityProvider provider = sequence.getProvider();
                            this.abilityManager.execute(user, sequence, provider);
                            return;
                        }

                        handle.getExecuting().add(sequence);
                    }
                });
    }

    public void cleanup() {
        User.all().forEach(user ->
                user.getSequenceHandle().getExecuting().removeIf(Sequence::hasExpired));
    }

    @Override
    public void register(AbilityProvider provider) {
        this.blueprints.add(provider.getSequence());
    }

    @Override
    public void unregister(AbilityProvider provider) {
        this.blueprints.removeIf(blueprint -> blueprint.getProvider().equals(provider));
    }

    @Override
    public void unregister(Class<? extends AbilityProvider> providerClass) {
        this.blueprints.removeIf(blueprint -> blueprint.getProvider().getClass().equals(providerClass));
    }

}

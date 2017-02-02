/*
 * MIT License
 *
 * Copyright (c) 2016 Chris Martin (OmniCypher-), Dylan Curzon (curz46), Connor Hartley (ConnorHartley)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.abilityapi.abilityapi.user;

import com.github.abilityapi.abilityapi.ability.Ability;
import com.github.abilityapi.abilityapi.sequence.SequenceHandle;
import org.spongepowered.api.entity.living.player.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class User extends BaseUser {

    private static final List<User> users = new ArrayList<>();
    private final List<Object> instances = new ArrayList<>();

    private final List<Ability> abilities = new ArrayList<>();
    private final SequenceHandle sequenceHandle = new SequenceHandle();

    public User(Player player) {
        super(player);
    }

    /**
     * @return A list of all currently instanced users.
     */
    public static List<User> all() {
        return users;
    }

    /**
     * @return A user instance of the player, either created or pre-existing.
     */
    public static User get(Player player) {
        Optional<User> optional = users.stream()
                .filter(user -> user.getPlayer().equals(player))
                .findFirst();

        if (!optional.isPresent()) {
            User user = new User(player);
            users.add(user);
            return user;
        }

        return optional.get();
    }

    /**
     * Remove any user instance attached to the player.
     *
     * @return true if any instances were removed.
     */
    public static boolean remove(Player player) {
        return users.removeIf(user -> user.getPlayer().equals(player));
    }

    public static boolean destroy(Player player) {
        return remove(player);
    }

    public static boolean annihilate(Player player) {
        return remove(player);
    }

    public static boolean obliterate(Player player) {
        return remove(player);
    }

    public <T> T getInstance(Class<T> clazz) {
        //noinspection unchecked
        Optional<T> optional = (Optional<T>) this.instances.stream()
                .filter(instance -> instance.getClass().equals(clazz))
                .findFirst();

        if (!optional.isPresent()) {
            T instance;
            Constructor<T> constructor;

            try {
                constructor = clazz.getConstructor(Player.class);
            } catch (NoSuchMethodException ex) {
                throw new InvalidUserBaseException("A single-parameter constructor of Player instance does not exist!");
            }

            try {
                instance = constructor.newInstance(this.player);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new InvalidUserBaseException("Failed to create a UserBase instance!");
            }

            this.instances.add(instance);
            return instance;
        }

        return optional.get();
    }

    /**
     * @param clazz The {@link Ability} class to filter for.
     * @return True, if the user contains any {@link Ability} instances.
     */
    public boolean hasAbility(Class<? extends Ability> clazz) {
        return !this.getAbilities().isEmpty();
    }

    /**
     * @param clazz The {@link Ability} class to filter for.
     * @return All executing {@link Ability} instances on this User, for a specific class.
     */
    public List<Ability> getAbilities(Class<? extends Ability> clazz) {
        return this.abilities.stream()
                .filter(ability -> ability.getClass().equals(clazz))
                .collect(Collectors.toList());
    }

    /**
     * @return All executing {@link Ability} instances on this User.
     */
    public List<Ability> getAbilities() {
        return this.abilities;
    }

    public SequenceHandle getSequenceHandle() {
        return this.sequenceHandle;
    }

}

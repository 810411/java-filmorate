package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public List<User> getAll() {
        return (List<User>) users.values();
    }

    @Override
    public Optional<User> get(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> add(User user) {
        user.setId(UUID.randomUUID());
        return Optional.ofNullable(users.put(user.getId(), user));
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.ofNullable(users.replace(user.getId(), user));
    }

    @Override
    public Optional<User> delete(UUID id) {
        return Optional.ofNullable(users.remove(id));
    }
}

package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStorage {
    List<User> getAll();

    Optional<User> get(UUID id);

    Optional<User> add(User user);

    Optional<User> update(User user);

    Optional<User> delete(UUID id);
}

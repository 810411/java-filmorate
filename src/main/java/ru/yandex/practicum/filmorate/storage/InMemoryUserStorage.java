package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage extends AbstractInMemoryStorage implements UserStorage {
    private final static Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> get(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User add(User user) {
        user.setId(getNextIndex());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException();
        }
        users.replace(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> delete(int id) {
        return Optional.ofNullable(users.remove(id));
    }

}

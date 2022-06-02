package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userStorage.getAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        return userStorage.get(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user = userStorage.add(user);
        log.info("New user with id = {} created", user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        user = userStorage.update(user);
        log.info("User with id = {} updated", user.getId());
        return user;
    }

    @GetMapping("{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
        log.info("Friend with id = {} added to user with id = {}", friendId, id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
        log.info("Friend with id = {} removed user with id = {}", friendId, id);
    }

}

package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        user.setId(users.size() + 1);
        users.add(user);
        log.info("New user with id = {} created", user.getId());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid User user) {
        if (user.getId() > users.size()) {
            String message = String.format("User with id = %d not found", user.getId());
            log.warn(message);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

        users.set(user.getId() - 1, user);
        log.info("User with id = {} updated", user.getId());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

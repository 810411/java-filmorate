package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public ResponseEntity<List<Film>> findAll() {
        return new ResponseEntity<>(filmStorage.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Film> add(@RequestBody @Valid Film film) {
        film = filmStorage.add(film).orElseThrow(IllegalArgumentException::new);
        log.info("New film with id = {} added into store", film.getId());

        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody @Valid Film film) {
        film = filmStorage.update(film).orElseThrow(IllegalArgumentException::new);
        log.info("Film with id = {} updated", film.getId());

        return new ResponseEntity<>(film, HttpStatus.OK);
    }
}

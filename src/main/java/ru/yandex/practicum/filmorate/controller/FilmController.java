package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final List<Film> films = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Film>> findAll() {
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Film> add(@RequestBody @Valid Film film) {
        film.setId(films.size() + 1);
        films.add(film);
        log.info("New film with id = {} added into store", film.getId());

        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody @Valid Film film) {
        if (film.getId() > films.size()) {
            String message = String.format("Film with id = %d not founded in storage", film.getId());
            log.warn(message);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

        films.set(film.getId() - 1, film);
        log.info("Film with id = {} updated", film.getId());

        return new ResponseEntity<>(film, HttpStatus.OK);
    }
}

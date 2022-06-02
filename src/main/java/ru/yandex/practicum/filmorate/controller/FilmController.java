package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmStorage.getAll();
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable int id) {
        return filmStorage.get(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        film = filmStorage.add(film);
        System.out.println(film);
        log.info("New film with id = {} added into store", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        film = filmStorage.update(film);
        log.info("Film with id = {} updated", film.getId());
        return film;
    }

    @GetMapping("/popular")
    public List<Film> getMostPopular(@RequestParam Optional<Integer> count) {
        return filmService.getMostPopular(count.orElse(10));
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable int id, @PathVariable int userId) {
        filmService.like(id, userId);
        log.info("Film with id = {} liked by user with id = {}", id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable int id, @PathVariable int userId) {
        filmService.unlike(id, userId);
        log.info("Film with id = {} unliked by user with id = {}", id, userId);
    }
}

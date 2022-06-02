package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Set<Film> getMostPopular(int count) {
        return filmStorage.getAll().stream()
                .filter(film -> film.getLikes() != null && film.getLikes().size() > 0)
                .sorted(Comparator.comparingInt(film -> film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toSet());
    }

    public void like(int filmId, int userId) {
        Film film = filmStorage.get(filmId).orElseThrow(NotFoundException::new);
        Set<Integer> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
        film.setLikes(likes);
    }

    public void unlike(int filmId, int userId) {
        Film film = filmStorage.get(filmId).orElseThrow(NotFoundException::new);
        Set<Integer> likes = film.getLikes();
        if (likes == null)
            throw new NotFoundException();
        likes.remove(userId);
    }
}

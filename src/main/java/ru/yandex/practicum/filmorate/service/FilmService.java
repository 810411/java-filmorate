package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getMostPopular() {
        return filmStorage.getAll().stream()
                .sorted(Comparator.comparingInt(film -> film.getLikes().size()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public void like(UUID filmId, UUID userId, int rating) {
        Film film = filmStorage.get(filmId).orElseThrow(IllegalArgumentException::new);
        Set<UUID> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
        film.setLikes(likes);
    }

    public void unlike(UUID filmId, UUID userId) {
        Film film = filmStorage.get(filmId).orElseThrow(IllegalArgumentException::new);
        Set<UUID> likes = film.getLikes();
        if (likes == null)
            return;
        likes.remove(userId);
    }
}

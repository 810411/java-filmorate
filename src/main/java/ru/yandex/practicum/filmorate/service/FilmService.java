package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getMostPopular(int count) {
        return Stream.concat(
                        filmStorage.getAll().stream()
                                .filter(film -> film.getLikes() != null && film.getLikes().size() > 0)
                                .sorted(Comparator.comparingInt(film -> film.getLikes().size())),
                        filmStorage.getAll().stream()
                                .filter(film -> film.getRate() != null)
                )
                .limit(count)
                .collect(Collectors.toList());
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

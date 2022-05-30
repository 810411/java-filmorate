package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmStorage {
    List<Film> getAll();

    Optional<Film> get(UUID id);

    Optional<Film> add(Film film);

    Optional<Film> update(Film film);

    Optional<Film> delete(UUID id);
}

package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getAll();

    Optional<Film> get(int id);

    Film add(Film film);

    Film update(Film film);

    Optional<Film> delete(int id);
}

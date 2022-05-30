package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<UUID, Film> films = new HashMap<>();

    @Override
    public List<Film> getAll() {
        return (List<Film>) films.values();
    }

    @Override
    public Optional<Film> get(UUID id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Optional<Film> add(Film film) {
        film.setId(UUID.randomUUID());
        return Optional.ofNullable(films.put(film.getId(), film));
    }

    @Override
    public Optional<Film> update(Film film) {
        return Optional.ofNullable(films.replace(film.getId(), film));
    }

    @Override
    public Optional<Film> delete(UUID id) {
        return Optional.ofNullable(films.remove(id));
    }
}

package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage extends AbstractInMemoryStorage implements FilmStorage {

    private final static Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> get(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film add(Film film) {
        film.setId(getNextIndex());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException();
        }
        films.replace(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> delete(int id) {
        return Optional.ofNullable(films.remove(id));
    }
}

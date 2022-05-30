package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest extends ModelTest<Film> {
    private Film film;

    @BeforeEach
    void beforeEach() {
        film = new Film();
        film.setId(1);
        film.setName("testName");
        film.setDescription("testDescription");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(Duration.ofHours(1));
    }

    @Test
    void testFilmSuccess() {
        assertTrue(validator.validate(film).isEmpty());
    }

    @Test
    void shouldDetectBlankName() {
        film.setName("");
        testPropertyValidation(film, "name", "must not be blank");
    }

    @Test
    void shouldDetectBlankDescription() {
        film.setDescription("");
        testPropertyValidation(film, "description", "must not be blank");
    }

    @Test
    void shouldDetectLongDescription() {
        film.setDescription("a".repeat(200));
        assertTrue(validator.validate(film).isEmpty());

        film.setDescription("a".repeat(201));
        testPropertyValidation(film, "description", "size must be between 0 and 200");
    }

    @Test
    void shouldDetectOldReleaseDate() {
        film.setReleaseDate(LocalDate.of(1895, 12,28));
        assertTrue(validator.validate(film).isEmpty());

        film.setReleaseDate(LocalDate.of(1895, 12,27));
        testPropertyValidation(film, "releaseDate", "date must not be before 1895-12-28");
    }

    @Test
    void shouldDetectNonPositiveDuration() {
        film.setDuration(Duration.ZERO);
        testPropertyValidation(film, "duration", "duration must be positive value");
    }
}
package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.util.validator.NotBefore;
import ru.yandex.practicum.filmorate.util.validator.Positive;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String description;
    @NotNull
    @NotBefore("1895-12-28")
    private LocalDate releaseDate;
    @NotNull
    @Positive
    private Duration duration;
}

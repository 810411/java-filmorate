package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.yandex.practicum.filmorate.util.validator.NotBefore;
import ru.yandex.practicum.filmorate.util.validator.Positive;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT )
    private Duration duration;
    private Set<Integer> likes;
}

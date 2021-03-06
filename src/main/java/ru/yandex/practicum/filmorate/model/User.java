package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    private int id;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^\\S+$")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;
    private Set<Integer> friends;
}

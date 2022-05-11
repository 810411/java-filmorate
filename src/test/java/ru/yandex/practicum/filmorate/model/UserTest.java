package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends ModelTest<User> {
    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setId(1);
        user.setEmail("test@email.ru");
        user.setLogin("testLogin");
        user.setBirthday(LocalDate.now());
    }

    @Test
    void testUserSuccess() {
        assertTrue(validator.validate(user).isEmpty());
    }

    @Test
    void shouldDetectBlankEmail() {
        user.setEmail("");
        testPropertyValidation(user, "email", "must not be blank");
    }

    @Test
    void shouldDetectIncorrectEmail() {
        user.setEmail("email");
        testPropertyValidation(user, "email", "must be a well-formed email address");
    }

    @Test
    void shouldDetectIncorrectLogin() {
        user.setLogin("");
        testPropertyValidation(user, "login", "must match \"^\\S+$\"");
    }

    @Test
    void shouldDetectIfBirthdayInFuture() {
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertTrue(validator.validate(user).isEmpty());

        user.setBirthday(LocalDate.of(3000, 1, 1));
        testPropertyValidation(user, "birthday", "must be a date in the past or in the present");
    }
}
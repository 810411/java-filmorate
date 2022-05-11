package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class ModelTest<T> {
    protected static Validator validator;

    @BeforeAll
    static void beforeAll() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    protected void testPropertyValidation(T testObj, String propertyName, String expectedMessage) {
        Set<ConstraintViolation<T>> violations = validator.validateProperty(testObj, propertyName);
        assertFalse(violations.isEmpty());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }
}

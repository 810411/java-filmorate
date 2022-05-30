package ru.yandex.practicum.filmorate.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class PositiveDurationValidator implements ConstraintValidator<Positive, Duration> {
    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return !(value.isZero() || value.isNegative());
    }
}

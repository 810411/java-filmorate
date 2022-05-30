package ru.yandex.practicum.filmorate.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateNotBeforeValidator implements ConstraintValidator<NotBefore, LocalDate> {
    private LocalDate dateFrom;

    @Override
    public void initialize(NotBefore constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        dateFrom = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return !value.isBefore(dateFrom);
    }
}

package ru.yandex.practicum.filmorate.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveDurationValidator.class)
@Documented
public @interface Positive {
    String message() default "duration must be positive value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

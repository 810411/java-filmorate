package ru.yandex.practicum.filmorate.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The value string must represent a valid date and is parsed using
 * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}
 * ("yyyy-MM-dd").
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateNotBeforeValidator.class)
@Documented
public @interface NotBefore {
    String message() default "date must not be before {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();
}

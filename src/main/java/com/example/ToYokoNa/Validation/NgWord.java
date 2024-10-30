package com.example.ToYokoNa.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={NgWordValidator.class})
public @interface NgWord {
    String message() default "NGワードが含まれています";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

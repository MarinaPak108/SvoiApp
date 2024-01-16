package com.svoiapp.exception;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileNumberValidator.class)
public @interface StartsWith {
    String value() default "";

    String message() default "Номер сотовго телефона должен начинаться с 010.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

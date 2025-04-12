package com.example.hms.staffservice.common.validator.annotation;

import com.example.hms.staffservice.common.validator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ METHOD, FIELD, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();
    String[] subset() default {};
    String message() default "Invalid value";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

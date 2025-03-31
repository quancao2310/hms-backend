package com.example.hms.patientfacingservice.auth.validators.annotations;

import com.example.hms.patientfacingservice.auth.validators.ValidPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, one digit, and one special character";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

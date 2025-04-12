package com.example.hms.staffservice.staff.validator.annotation;

import com.example.hms.staffservice.staff.validator.MatchingPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatchingPasswordValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchingPassword {
    String message() default "New password and confirm password do not match";
    String newPassword();
    String confirmPassword();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        MatchingPassword[] value();
    }
}

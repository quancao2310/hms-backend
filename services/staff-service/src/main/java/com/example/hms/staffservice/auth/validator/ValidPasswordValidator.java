package com.example.hms.staffservice.auth.validator;

import com.example.hms.staffservice.auth.validator.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&~*+=<>.,?;:'\"`_()\\[\\]{}|/\\\\-]).{8,}$"
    );

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
}

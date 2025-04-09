package com.example.hms.staffservice.common.validator;

import com.example.hms.staffservice.common.validator.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private List<String> enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        if (constraintAnnotation.subset().length > 0) {
            enumValues = List.of(constraintAnnotation.subset());
        } else {
            enumValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .toList();
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (!enumValues.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Value must be any of: " + String.join(", ", enumValues)
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}

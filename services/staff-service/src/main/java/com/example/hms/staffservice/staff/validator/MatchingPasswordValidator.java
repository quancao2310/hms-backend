package com.example.hms.staffservice.staff.validator;

import com.example.hms.staffservice.staff.validator.annotation.MatchingPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPassword, Object> {

    private String newPassword;
    private String confirmPassword;

    @Override
    public void initialize(MatchingPassword constraintAnnotation) {
        this.newPassword = constraintAnnotation.newPassword();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object newPasswordValue = new BeanWrapperImpl(value).getPropertyValue(newPassword);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

        if (newPasswordValue != null) {
            return newPasswordValue.equals(confirmPasswordValue);
        } else {
            return confirmPasswordValue == null;
        }
    }
}

package com.xpronto.webgestao.api.dtos.validatiors;

import java.util.regex.Pattern;

import com.xpronto.webgestao.api.dtos.annotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>?,./]).{8,}$";

    private final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return pattern.matcher(value).matches();
    }

}

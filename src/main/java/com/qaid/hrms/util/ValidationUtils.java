package com.qaid.hrms.util;

import com.qaid.hrms.exception.BadRequestException;
import org.springframework.util.StringUtils;

public class ValidationUtils {
    
    public static void validateRequired(String field, String fieldName) {
        if (!StringUtils.hasText(field)) {
            throw new BadRequestException(fieldName + " is required");
        }
    }

    public static void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+?[1-9]\\d{1,14}$";
        if (!phoneNumber.matches(phoneRegex)) {
            throw new BadRequestException("Invalid phone number format");
        }
    }

    public static void validatePassword(String password) {
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }
    }
}
package com.qaid.hrms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AzurePasswordEncoder implements PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(AzurePasswordEncoder.class);
    private final EncryptionService encryptionService;
    private final BCryptPasswordEncoder bcryptEncoder;

    public AzurePasswordEncoder(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
        this.bcryptEncoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String bcryptHash = bcryptEncoder.encode(rawPassword);
        try {
            return encryptionService.encryptPassword(bcryptHash);
        } catch (Exception e) {
            logger.warn("Error encrypting password, falling back to BCrypt: {}", e.getMessage());
            return bcryptHash;
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // First try direct BCrypt match for passwords from data.sql
        if (encodedPassword.startsWith("$2a$")) {
            return bcryptEncoder.matches(rawPassword, encodedPassword);
        }

        // Then try decryption for passwords created through the application
        try {
            String decryptedHash = encryptionService.decryptPassword(encodedPassword);
            return bcryptEncoder.matches(rawPassword, decryptedHash);
        } catch (Exception e) {
            logger.warn("Error decrypting password: {}", e.getMessage());
            // As a fallback, try direct matching
            return bcryptEncoder.matches(rawPassword, encodedPassword);
        }
    }
}
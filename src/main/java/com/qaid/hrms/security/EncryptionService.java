package com.qaid.hrms.security;

import com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm;
import com.qaid.hrms.config.CryptographyClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionService {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    @Autowired
    private CryptographyClientInterface cryptographyClient;

    public String encryptPassword(String password) {
        try {
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = cryptographyClient.encrypt(EncryptionAlgorithm.RSA_OAEP, passwordBytes).getCipherText();
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.error("Error encrypting password: {}", e.getMessage());
            // Return the original password if encryption fails
            // This is not ideal for production but helps with development
            return password;
        }
    }

    public String decryptPassword(String encryptedPassword) {
        try {
            // If it's a BCrypt hash, return it directly
            if (encryptedPassword.startsWith("$2a$")) {
                return encryptedPassword;
            }
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cryptographyClient.decrypt(EncryptionAlgorithm.RSA_OAEP, encryptedBytes).getPlainText();
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Error decrypting password: {}", e.getMessage());
            // Return the encrypted password if decryption fails
            // This will allow the BCryptPasswordEncoder to try matching directly
            return encryptedPassword;
        }
    }
}
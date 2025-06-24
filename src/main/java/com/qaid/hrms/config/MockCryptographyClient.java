package com.qaid.hrms.config;

import com.azure.core.util.Context;
import com.azure.security.keyvault.keys.cryptography.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * A mock implementation of CryptographyClient for local development.
 * This implementation uses a simple AES encryption for local testing.
 */
public class MockCryptographyClient implements CryptographyClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(MockCryptographyClient.class);
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128; // Using 128 for wider compatibility
    
    private final SecretKey secretKey;
    
    public MockCryptographyClient() {
        this.secretKey = generateKey();
        logger.info("Initialized MockCryptographyClient for local development");
    }
    
    private SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to generate key for MockCryptographyClient", e);
            throw new RuntimeException("Failed to initialize MockCryptographyClient", e);
        }
    }
    
    @Override
    public EncryptResult encrypt(EncryptionAlgorithm algorithm, byte[] plaintext) {
        Objects.requireNonNull(plaintext, "Plaintext cannot be null");
        logger.debug("Mock encrypting data with algorithm: {}", algorithm);
        
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext);
            
            return new EncryptResult(encryptedBytes, algorithm, "mock-key-id");
        } catch (Exception e) {
            logger.error("Error in mock encryption", e);
            throw new RuntimeException("Mock encryption failed", e);
        }
    }
    
    @Override
    public DecryptResult decrypt(EncryptionAlgorithm algorithm, byte[] ciphertext) {
        Objects.requireNonNull(ciphertext, "Ciphertext cannot be null");
        logger.debug("Mock decrypting data with algorithm: {}", algorithm);
        
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(ciphertext);
            
            return new DecryptResult(decryptedBytes, algorithm, "mock-key-id");
        } catch (Exception e) {
            logger.error("Error in mock decryption", e);
            throw new RuntimeException("Mock decryption failed", e);
        }
    }
    
    @Override
    public EncryptResult encrypt(EncryptionAlgorithm algorithm, byte[] plaintext, Context context) {
        return encrypt(algorithm, plaintext);
    }
    
    @Override
    public DecryptResult decrypt(EncryptionAlgorithm algorithm, byte[] ciphertext, Context context) {
        return decrypt(algorithm, ciphertext);
    }
    
    // Unsupported operations - these are not used by the EncryptionService
    
    @Override
    public SignResult sign(SignatureAlgorithm algorithm, byte[] digest) {
        throw new UnsupportedOperationException("Sign operation not supported in MockCryptographyClient");
    }
    
    @Override
    public VerifyResult verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature) {
        throw new UnsupportedOperationException("Verify operation not supported in MockCryptographyClient");
    }
    
    @Override
    public WrapResult wrapKey(KeyWrapAlgorithm algorithm, byte[] key) {
        throw new UnsupportedOperationException("WrapKey operation not supported in MockCryptographyClient");
    }
    
    @Override
    public UnwrapResult unwrapKey(KeyWrapAlgorithm algorithm, byte[] encryptedKey) {
        throw new UnsupportedOperationException("UnwrapKey operation not supported in MockCryptographyClient");
    }
    
    @Override
    public SignResult sign(SignatureAlgorithm algorithm, byte[] digest, Context context) {
        throw new UnsupportedOperationException("Sign operation not supported in MockCryptographyClient");
    }
    
    @Override
    public VerifyResult verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature, Context context) {
        throw new UnsupportedOperationException("Verify operation not supported in MockCryptographyClient");
    }
    
    @Override
    public WrapResult wrapKey(KeyWrapAlgorithm algorithm, byte[] key, Context context) {
        throw new UnsupportedOperationException("WrapKey operation not supported in MockCryptographyClient");
    }
    
    @Override
    public UnwrapResult unwrapKey(KeyWrapAlgorithm algorithm, byte[] encryptedKey, Context context) {
        throw new UnsupportedOperationException("UnwrapKey operation not supported in MockCryptographyClient");
    }
}
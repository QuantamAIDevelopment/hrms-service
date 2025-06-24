package com.qaid.hrms.config;

import com.azure.core.util.Context;
import com.azure.security.keyvault.keys.cryptography.models.*;

/**
 * Interface that defines the methods used by the EncryptionService.
 * This allows us to provide both real and mock implementations.
 */
public interface CryptographyClientInterface {
    
    /**
     * Encrypts the specified plaintext using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param plaintext The content to be encrypted.
     * @return The encrypted result.
     */
    EncryptResult encrypt(EncryptionAlgorithm algorithm, byte[] plaintext);
    
    /**
     * Encrypts the specified plaintext using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param plaintext The content to be encrypted.
     * @param context Additional context that is passed through the HTTP pipeline during the service call.
     * @return The encrypted result.
     */
    EncryptResult encrypt(EncryptionAlgorithm algorithm, byte[] plaintext, Context context);
    
    /**
     * Decrypts the specified ciphertext using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param ciphertext The content to be decrypted.
     * @return The decrypted result.
     */
    DecryptResult decrypt(EncryptionAlgorithm algorithm, byte[] ciphertext);
    
    /**
     * Decrypts the specified ciphertext using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param ciphertext The content to be decrypted.
     * @param context Additional context that is passed through the HTTP pipeline during the service call.
     * @return The decrypted result.
     */
    DecryptResult decrypt(EncryptionAlgorithm algorithm, byte[] ciphertext, Context context);
    
    /**
     * Creates a signature from the digest using the specified algorithm.
     *
     * @param algorithm The signing algorithm to use.
     * @param digest The content to be signed.
     * @return The signature created from the digest.
     */
    SignResult sign(SignatureAlgorithm algorithm, byte[] digest);
    
    /**
     * Creates a signature from the digest using the specified algorithm.
     *
     * @param algorithm The signing algorithm to use.
     * @param digest The content to be signed.
     * @param context Additional context that is passed through the HTTP pipeline during the service call.
     * @return The signature created from the digest.
     */
    SignResult sign(SignatureAlgorithm algorithm, byte[] digest, Context context);
    
    /**
     * Verifies the signature against the specified digest using the specified algorithm.
     *
     * @param algorithm The signing algorithm to use.
     * @param digest The content that was signed.
     * @param signature The signature to be verified.
     * @return The verification result.
     */
    VerifyResult verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature);
    
    /**
     * Verifies the signature against the specified digest using the specified algorithm.
     *
     * @param algorithm The signing algorithm to use.
     * @param digest The content that was signed.
     * @param signature The signature to be verified.
     * @param context Additional context that is passed through the HTTP pipeline during the service call.
     * @return The verification result.
     */
    VerifyResult verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature, Context context);
    
    /**
     * Wraps the specified key using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param key The key to be wrapped.
     * @return The wrapped key result.
     */
    WrapResult wrapKey(KeyWrapAlgorithm algorithm, byte[] key);
    
    /**
     * Wraps the specified key using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param key The key to be wrapped.
     * @param context Additional context that is passed through the HTTP pipeline during the service call.
     * @return The wrapped key result.
     */
    WrapResult wrapKey(KeyWrapAlgorithm algorithm, byte[] key, Context context);
    
    /**
     * Unwraps the specified wrapped key using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param encryptedKey The key to be unwrapped.
     * @return The unwrapped key result.
     */
    UnwrapResult unwrapKey(KeyWrapAlgorithm algorithm, byte[] encryptedKey);
    
    /**
     * Unwraps the specified wrapped key using the specified algorithm.
     *
     * @param algorithm The encryption algorithm to use.
     * @param encryptedKey The key to be unwrapped.
     * @param context Additional context that is passed through the HTTP pipeline during the service call.
     * @return The unwrapped key result.
     */
    UnwrapResult unwrapKey(KeyWrapAlgorithm algorithm, byte[] encryptedKey, Context context);
}
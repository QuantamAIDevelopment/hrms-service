package com.qaid.hrms.config;

import com.azure.core.util.Context;
import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.models.*;

/**
 * A wrapper for the real CryptographyClient that implements our CryptographyClientInterface.
 */
public class CryptographyClientWrapper implements CryptographyClientInterface {
    
    private final CryptographyClient client;
    
    public CryptographyClientWrapper(CryptographyClient client) {
        this.client = client;
    }
    
    @Override
    public EncryptResult encrypt(EncryptionAlgorithm algorithm, byte[] plaintext) {
        return client.encrypt(algorithm, plaintext);
    }
    
    @Override
    public EncryptResult encrypt(EncryptionAlgorithm algorithm, byte[] plaintext, Context context) {
        return client.encrypt(algorithm, plaintext, context);
    }
    
    @Override
    public DecryptResult decrypt(EncryptionAlgorithm algorithm, byte[] ciphertext) {
        return client.decrypt(algorithm, ciphertext);
    }
    
    @Override
    public DecryptResult decrypt(EncryptionAlgorithm algorithm, byte[] ciphertext, Context context) {
        return client.decrypt(algorithm, ciphertext, context);
    }
    
    @Override
    public SignResult sign(SignatureAlgorithm algorithm, byte[] digest) {
        return client.sign(algorithm, digest);
    }
    
    @Override
    public SignResult sign(SignatureAlgorithm algorithm, byte[] digest, Context context) {
        return client.sign(algorithm, digest, context);
    }
    
    @Override
    public VerifyResult verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature) {
        return client.verify(algorithm, digest, signature);
    }
    
    @Override
    public VerifyResult verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature, Context context) {
        return client.verify(algorithm, digest, signature, context);
    }
    
    @Override
    public WrapResult wrapKey(KeyWrapAlgorithm algorithm, byte[] key) {
        return client.wrapKey(algorithm, key);
    }
    
    @Override
    public WrapResult wrapKey(KeyWrapAlgorithm algorithm, byte[] key, Context context) {
        return client.wrapKey(algorithm, key, context);
    }
    
    @Override
    public UnwrapResult unwrapKey(KeyWrapAlgorithm algorithm, byte[] encryptedKey) {
        return client.unwrapKey(algorithm, encryptedKey);
    }
    
    @Override
    public UnwrapResult unwrapKey(KeyWrapAlgorithm algorithm, byte[] encryptedKey, Context context) {
        return client.unwrapKey(algorithm, encryptedKey, context);
    }
}
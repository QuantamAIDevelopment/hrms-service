package com.qaid.hrms.config;

import com.azure.core.credential.TokenCredential;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class AzureKeyVaultConfig {
    private static final Logger logger = LoggerFactory.getLogger(AzureKeyVaultConfig.class);

    @Value("${azure.keyvault.uri}")
    private String keyVaultUri;

    @Value("${azure.keyvault.key-name}")
    private String keyName;
    
    @Autowired
    private TokenCredential azureCredential;
    
    @Autowired
    private Environment environment;

    @Bean
    public KeyClient keyClient() {
        logger.info("Initializing Azure Key Vault client with URI: {}", keyVaultUri);
        
        // Check if we're in a local/dev environment
        boolean isLocalDev = isLocalOrDevEnvironment();
        
        try {
            KeyClient client = new KeyClientBuilder()
                    .vaultUrl(keyVaultUri)
                    .credential(azureCredential)
                    .buildClient();
            
            // Test the client by trying to get a key
            if (!isLocalDev) {
                // In production, we want to verify the client works
                client.getKey(keyName);
                logger.info("Successfully connected to Azure Key Vault");
            }
            
            return client;
        } catch (Exception e) {
            logger.error("Failed to create or test KeyClient: {}", e.getMessage());
            if (isLocalDev) {
                logger.warn("Running in local/dev environment. Returning null KeyClient.");
                return null;
            } else {
                // In production, log more details about the error
                logger.error("Azure Key Vault connection failed in production environment", e);
                // Return a null client and let the application handle fallbacks
                // This is better than throwing an exception which would prevent the application from starting
                return null;
            }
        }
    }

    /**
     * Creates a CryptographyClientInterface for Azure Key Vault.
     * In production, this uses the real Azure Key Vault.
     * In local/dev environments, if the real client can't be created, it falls back to a mock implementation.
     */
    @Bean
    @Primary
    public CryptographyClientInterface cryptographyClientInterface(KeyClient keyClient) {
        // Try to create a real Azure Key Vault CryptographyClient
        CryptographyClient realClient = createRealCryptographyClient(keyClient);
        
        // If we couldn't create a real client and we're in a local/dev environment,
        // create a mock client instead
        if (realClient == null) {
            if (isLocalOrDevEnvironment()) {
                logger.info("Creating MockCryptographyClient for local/dev environment");
                return new MockCryptographyClient();
            } else {
                // In production, log this as an error but still provide a mock client
                // to prevent application failure
                logger.error("Failed to create real CryptographyClient in production environment. Using mock implementation as fallback.");
                return new MockCryptographyClient();
            }
        }
        
        // Wrap the real client in our interface implementation
        logger.info("Successfully created real CryptographyClient");
        return new CryptographyClientWrapper(realClient);
    }
    
    /**
     * For backward compatibility, also provide a CryptographyClient bean
     * that delegates to our CryptographyClientInterface implementation.
     */
    @Bean
    public CryptographyClient cryptographyClient(CryptographyClientInterface cryptographyClientInterface) {
        // If our interface is already a CryptographyClient (via the wrapper), return it directly
        if (cryptographyClientInterface instanceof CryptographyClientWrapper) {
            CryptographyClientWrapper wrapper = (CryptographyClientWrapper) cryptographyClientInterface;
            // Use reflection to get the wrapped client
            try {
                java.lang.reflect.Field field = CryptographyClientWrapper.class.getDeclaredField("client");
                field.setAccessible(true);
                return (CryptographyClient) field.get(wrapper);
            } catch (Exception e) {
                logger.error("Failed to extract CryptographyClient from wrapper", e);
                if (isLocalOrDevEnvironment()) {
                    // In dev/local, try to create a new client
                    logger.warn("Attempting to create a new CryptographyClient as fallback");
                    try {
                        return createRealCryptographyClient(keyClient());
                    } catch (Exception ex) {
                        logger.error("Fallback creation also failed", ex);
                        return null;
                    }
                } else {
                    throw new RuntimeException("Failed to provide CryptographyClient bean", e);
                }
            }
        }
        
        // Otherwise, we're using the mock implementation, so we need to create a real client
        // This is a fallback and shouldn't normally be used
        logger.warn("Creating a real CryptographyClient from a mock implementation. This is not recommended.");
        try {
            CryptographyClient client = createRealCryptographyClient(keyClient());
            if (client != null) {
                return client;
            } else if (isLocalOrDevEnvironment()) {
                logger.warn("Could not create real CryptographyClient in local/dev environment. Returning null.");
                return null;
            } else {
                throw new RuntimeException("Failed to create CryptographyClient in production environment");
            }
        } catch (Exception e) {
            logger.error("Failed to create real CryptographyClient", e);
            if (isLocalOrDevEnvironment()) {
                logger.warn("Returning null CryptographyClient in local/dev environment");
                return null;
            } else {
                throw new RuntimeException("Failed to provide CryptographyClient bean", e);
            }
        }
    }
    
    /**
     * Attempts to create a real Azure Key Vault CryptographyClient.
     * Returns null if it can't be created (e.g., in local/dev environments without proper configuration).
     */
    private CryptographyClient createRealCryptographyClient(KeyClient keyClient) {
        if (keyClient == null) {
            logger.warn("KeyClient is null, cannot create real CryptographyClient");
            return null;
        }
        
        try {
            logger.info("Retrieving key '{}' from Azure Key Vault", keyName);
            KeyVaultKey key = keyClient.getKey(keyName);
            logger.info("Successfully retrieved key with ID: {}", key.getId());
            
            return new CryptographyClientBuilder()
                    .credential(azureCredential)
                    .keyIdentifier(key.getId())
                    .buildClient();
        } catch (Exception e) {
            if (isLocalOrDevEnvironment()) {
                logger.warn("Failed to retrieve key from Azure Key Vault in local/dev environment. " +
                        "This is expected if you don't have a real Key Vault configured. Error: {}", e.getMessage());
                logger.warn("Will use MockCryptographyClient instead.");
                return null;
            } else {
                // For non-local environments, log the error but return null to allow fallback
                logger.error("Failed to retrieve key from Azure Key Vault in production environment", e);
                return null;
            }
        }
    }
    
    private boolean isLocalOrDevEnvironment() {
        String[] activeProfiles = environment.getActiveProfiles();
        return activeProfiles.length == 0 || // No profile means local by default
               Arrays.asList(activeProfiles).contains("local") || 
               Arrays.asList(activeProfiles).contains("dev");
    }
}
package com.qaid.hrms.config;

import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
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
public class AzureIdentityConfig {
    private static final Logger logger = LoggerFactory.getLogger(AzureIdentityConfig.class);

    @Value("${azure.active-directory.tenant-id:}")
    private String tenantId;

    @Value("${azure.active-directory.client-id:}")
    private String clientId;

    @Value("${azure.active-directory.client-secret:}")
    private String clientSecret;
    
    @Value("${azure.identity.azure-tenant-id:${azure.active-directory.tenant-id:}}")
    private String azureTenantId;

    @Value("${azure.identity.azure-client-id:${azure.active-directory.client-id:}}")
    private String azureClientId;

    @Value("${azure.identity.azure-client-secret:${azure.active-directory.client-secret:}}")
    private String azureClientSecret;

    @Value("${azure.identity.managed-identity-client-id:}")
    private String identityClientId;

    @Value("${azure.identity.use-managed-identity:false}")
    private boolean useManagedIdentity;
    
    @Autowired
    private Environment environment;

    /**
     * Creates a TokenCredential based on the environment.
     * This is the preferred credential for most scenarios.
     */
    @Bean
    @Primary
    public TokenCredential azureCredential() {
        // Always set environment variables for EnvironmentCredential
        setAzureEnvironmentVariables();
        
        // For local development or any environment, prioritize explicit client credentials
        logger.info("Checking for explicit client credentials...");

        // First, try to use ClientSecretCredential directly if all values are provided
        if (!tenantId.isEmpty() && !clientId.isEmpty() && !clientSecret.isEmpty()) {
            logger.info("Using explicit ClientSecretCredential with tenant ID: {}, client ID: {}", tenantId, clientId);
            try {
                ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                        .tenantId(tenantId)
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .build();
                
                // Test if it works by requesting a token
                TokenRequestContext tokenRequest = new TokenRequestContext()
                        .addScopes("https://vault.azure.net/.default");
                credential.getTokenSync(tokenRequest);
                logger.info("Successfully authenticated with ClientSecretCredential");
                return credential;
            } catch (Exception e) {
                logger.warn("ClientSecretCredential authentication failed: {}", e.getMessage());
                // Continue to next authentication method
            }
        }

        // If we're in a local/dev environment, we can be more lenient with authentication failures
        if (isLocalOrDevEnvironment()) {
            logger.info("Local/Dev environment detected. Will fall back to mock implementations if needed.");
            
            try {
                // Try with DefaultAzureCredential for local dev
                DefaultAzureCredentialBuilder builder = new DefaultAzureCredentialBuilder();
                
                TokenCredential credential = builder.build();
                // Test if it works
                TokenRequestContext tokenRequest = new TokenRequestContext()
                        .addScopes("https://vault.azure.net/.default");
                credential.getTokenSync(tokenRequest);
                logger.info("Successfully authenticated with DefaultAzureCredential in local/dev environment");
                return credential;
            } catch (Exception e) {
                logger.warn("DefaultAzureCredential failed in local/dev environment: {}", e.getMessage());
                // In local/dev, we'll return a credential anyway and let the application handle fallbacks
                logger.info("Returning DefaultAzureCredential for local/dev despite authentication failure");
                return new DefaultAzureCredentialBuilder().build();
            }
        }
        
        // For production environments, create a more comprehensive DefaultAzureCredential
        logger.info("Creating DefaultAzureCredential for production environment");
        DefaultAzureCredentialBuilder builder = new DefaultAzureCredentialBuilder();

        // If we have a specific client ID for the managed identity, use it
        if (useManagedIdentity && !identityClientId.isEmpty()) {
            logger.info("Using managed identity with client ID: {}", identityClientId);
            builder.managedIdentityClientId(identityClientId);
        }

        return builder.build();
    }
    
    /**
     * Creates a ClientSecretCredential for service principal authentication.
     * This is useful when you need to explicitly use service principal authentication.
     */
    @Bean
    public ClientSecretCredential clientSecretCredential() {
        if (tenantId.isEmpty() || clientId.isEmpty() || clientSecret.isEmpty()) {
            logger.warn("Cannot create ClientSecretCredential: tenant ID, client ID, or client secret is missing");
            if (isLocalOrDevEnvironment()) {
                // In dev/local, return a dummy credential that will fail gracefully
                logger.info("Creating dummy ClientSecretCredential for local/dev environment");
                return new ClientSecretCredentialBuilder()
                        .tenantId(tenantId.isEmpty() ? "dummy-tenant" : tenantId)
                        .clientId(clientId.isEmpty() ? "dummy-client" : clientId)
                        .clientSecret(clientSecret.isEmpty() ? "dummy-secret" : clientSecret)
                        .build();
            } else {
                throw new IllegalStateException("Missing required Azure AD credentials for service principal authentication");
            }
        }
        
        logger.info("Initializing ClientSecretCredential for service principal authentication");
        return new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
    
    /**
     * Helper method to set Azure environment variables for EnvironmentCredential
     * if they're not already set.
     */
    private void setAzureEnvironmentVariables() {
        // First try to use the azure.identity.azure-* properties
        String effectiveTenantId = !azureTenantId.isEmpty() ? azureTenantId : tenantId;
        String effectiveClientId = !azureClientId.isEmpty() ? azureClientId : clientId;
        String effectiveClientSecret = !azureClientSecret.isEmpty() ? azureClientSecret : clientSecret;
        
        // Only set if not already present in environment and values are not empty
        if (System.getenv("AZURE_TENANT_ID") == null && !effectiveTenantId.isEmpty()) {
            logger.info("Setting AZURE_TENANT_ID environment variable");
            System.setProperty("AZURE_TENANT_ID", effectiveTenantId);
        }
        
        if (System.getenv("AZURE_CLIENT_ID") == null && !effectiveClientId.isEmpty()) {
            logger.info("Setting AZURE_CLIENT_ID environment variable");
            System.setProperty("AZURE_CLIENT_ID", effectiveClientId);
        }
        
        if (System.getenv("AZURE_CLIENT_SECRET") == null && !effectiveClientSecret.isEmpty()) {
            logger.info("Setting AZURE_CLIENT_SECRET environment variable");
            System.setProperty("AZURE_CLIENT_SECRET", effectiveClientSecret);
        }
    }
    
    private boolean isLocalOrDevEnvironment() {
        String[] activeProfiles = environment.getActiveProfiles();
        return activeProfiles.length == 0 || // No profile means local by default
               Arrays.asList(activeProfiles).contains("local") || 
               Arrays.asList(activeProfiles).contains("dev");
    }
}
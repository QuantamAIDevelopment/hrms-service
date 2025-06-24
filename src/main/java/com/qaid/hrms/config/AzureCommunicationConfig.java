package com.qaid.hrms.config;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class AzureCommunicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(AzureCommunicationConfig.class);

    @Value("${azure.communication.connection-string}")
    private String connectionString;

    @Value("${azure.communication.sender-address}")
    private String senderAddress;
    
    @Autowired
    private Environment environment;

    @Bean
    public EmailClient emailClient() {
        if (connectionString == null || connectionString.isEmpty()) {
            logger.warn("Azure Communication Services connection string is not configured");
            
            if (isLocalOrDevEnvironment()) {
                logger.info("Running in local/dev environment. Returning null EmailClient.");
                return null;
            } else {
                throw new IllegalStateException("Azure Communication Services connection string is required in production environment");
            }
        }
        
        logger.info("Initializing Azure Communication Services Email Client");
        try {
            return new EmailClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
        } catch (Exception e) {
            logger.error("Failed to initialize Azure Communication Services Email Client", e);
            
            if (isLocalOrDevEnvironment()) {
                logger.info("Running in local/dev environment. Returning null EmailClient.");
                return null;
            } else {
                throw e;
            }
        }
    }
    
    @Bean
    public String senderEmailAddress() {
        return senderAddress;
    }
    
    private boolean isLocalOrDevEnvironment() {
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.asList(activeProfiles).contains("local") || 
               Arrays.asList(activeProfiles).contains("dev");
    }
}
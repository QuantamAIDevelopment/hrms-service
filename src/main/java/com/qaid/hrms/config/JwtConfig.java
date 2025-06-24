package com.qaid.hrms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;


@Data
@Configuration
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtConfig {

    private String privateKeyPath;
    private String publicKeyPath;
    private long expiration = 86400000; // 24 hours in milliseconds
    private String issuer = "HRMS";

    private RefreshToken refreshToken = new RefreshToken();

    // Default constructor that sets up default paths
    public JwtConfig() {
        // Set default paths relative to user home directory
        String userHome = System.getProperty("user.home");
        String keysDir = userHome + File.separator + ".hrms" + File.separator + "keys";

        this.privateKeyPath = keysDir + File.separator + "private_key.pem";
        this.publicKeyPath = keysDir + File.separator + "public_key.pem";
    }

    @Data
    public static class RefreshToken {
        private long expiration = 604800000; // 7 days in milliseconds
    }

}

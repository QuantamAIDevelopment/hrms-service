package com.qaid.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Add import for spring-dotenv
import io.github.allanmarques83.DotEnvConfiguration;

@SpringBootApplication
public class HumanResourceManagementSystemApplication {

	public static void main(String[] args) {
		// Load .env file before starting Spring Boot
		SpringApplication.run(HumanResourceManagementSystemApplication.class, args);
	}

}

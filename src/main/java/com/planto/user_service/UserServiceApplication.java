package com.planto.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Main class for the User Service application.
 * This class serves as the entry point for the Spring Boot application.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @SpringBootApplication} - Indicates a Spring Boot application and enables auto-configuration.</li>
 *   <li>{@code @EnableWebSecurity} - Enables Spring Security's web security support.</li>
 * </ul>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Bootstraps the application using {@link SpringApplication#run(Class, String...)}.</li>
 * </ul>
 *
 * <p>Author:</p>
 * <ul>
 *   <li>Praful</li>
 * </ul>
 */
@SpringBootApplication
@EnableWebSecurity
public class UserServiceApplication {

	/**
	 * Main method to launch the Spring Boot application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
package com.planto.user_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * Configuration class for Spring Security.
 * This class defines the security settings and beans required for the application.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Configures HTTP security settings, including CSRF, session management, and endpoint authorization.</li>
 *   <li>Defines beans for authentication, password encoding, and user details management.</li>
 *   <li>Initializes default users during application startup.</li>
 * </ul>
 *
 * <p>Beans:</p>
 * <ul>
 *   <li>{@code authenticationJwtTokenFilter} - JWT token filter for request authentication.</li>
 *   <li>{@code defaultSecurityFilterChain} - Configures the security filter chain.</li>
 *   <li>{@code userDetailsService} - Manages user details using a JDBC data source.</li>
 *   <li>{@code initData} - CommandLineRunner to initialize default users.</li>
 *   <li>{@code passwordEncoder} - Encodes passwords using BCrypt.</li>
 *   <li>{@code authenticationManager} - Manages authentication processes.</li>
 * </ul>
 *
 * @author Praful
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * Bean for the JWT token filter.
     *
     * @return An instance of {@link AuthTokenFilter}.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http The {@link HttpSecurity} object to configure.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests(auth -> auth
                        // Allow only login and register publicly
                        .requestMatchers(HttpMethod.POST, "/users/login", "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/update/**").hasAnyRole("USER", "ADMIN")

                        // Secure all other endpoints
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures the user details service to manage users using a JDBC data source.
     *
     * @param dataSource The {@link DataSource} for accessing the database.
     * @return An instance of {@link UserDetailsService}.
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("select username,password,enabled from users where username=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("select username,authority from authorities where username=?");
        return userDetailsManager;
    }

    /**
     * Initializes default users during application startup.
     *
     * @param userDetailsService The {@link UserDetailsService} to manage users.
     * @return A {@link CommandLineRunner} to execute the initialization logic.
     */
    @Bean
    public CommandLineRunner initData(UserDetailsService userDetailsService) {
        return args -> {
            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;

            UserDetails user1 = User.withUsername("user1")
                    .password(passwordEncoder().encode("password1"))
                    .roles("USER")
                    .build();

            UserDetails admin = User.withUsername("admin")
                    .password(passwordEncoder().encode("adminPass"))
                    .roles("ADMIN")
                    .build();

            // Uncomment to create users during app startup
            // manager.createUser(user1);
            // manager.createUser(admin);
        };
    }

    /**
     * Bean for password encoding using BCrypt.
     *
     * @return An instance of {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for managing authentication processes.
     *
     * @param builder The {@link AuthenticationConfiguration} to configure.
     * @return An instance of {@link AuthenticationManager}.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
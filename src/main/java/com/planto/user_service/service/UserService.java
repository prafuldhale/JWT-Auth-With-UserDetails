package com.planto.user_service.service;

import com.planto.user_service.entity.Authorities;
import com.planto.user_service.entity.Users;
import com.planto.user_service.repository.AuthorityRepository;
import com.planto.user_service.repository.UserRepository;
import com.planto.user_service.repository.UsersRepository;
import com.planto.user_service.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing user-related operations.
 * This class provides methods for user registration, authentication, retrieval, and updates.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Registering new users with encoded passwords and default roles.</li>
 *   <li>Authenticating users and generating JWT tokens.</li>
 *   <li>Retrieving user details by ID.</li>
 *   <li>Updating user information.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@code UsersRepository} - Repository for user data.</li>
 *   <li>{@code AuthorityRepository} - Repository for user roles.</li>
 *   <li>{@code JwtUtils} - Utility for JWT token operations.</li>
 *   <li>{@code PasswordEncoder} - Encoder for hashing passwords.</li>
 *   <li>{@code AuthenticationManager} - Manager for authentication processes.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service} - Marks this class as a Spring service component.</li>
 *   <li>{@code @Transactional} - Ensures transactional behavior for specific methods.</li>
 * </ul>
 *
 * @author Praful
 */
@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository; // Repository for user data.

    @Autowired
    private AuthorityRepository authorityRepository; // Repository for user roles.

    @Autowired
    private JwtUtils jwtUtils; // Utility for JWT token operations.

    @Autowired
    private PasswordEncoder passwordEncoder; // Encoder for hashing passwords.

    @Autowired
    private AuthenticationManager authenticationManager; // Manager for authentication processes.

    /**
     * Registers a new user with encoded password and default role.
     *
     * @param user The user to register.
     * @return The registered user.
     */
    @Transactional
    public Users registerUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Assuming user is enabled by default
        Authorities authorities = new Authorities(user.getUsername(), "ROLE_USER");
        authorityRepository.save(authorities);
        userRepository.save(user);
        return user;
    }

    /**
     * Authenticates a user and generates a JWT token upon successful authentication.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A {@link ResponseEntity} containing the authentication response.
     */
    public ResponseEntity<Object> authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Fetch additional details from the authenticated user if needed
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate a JWT token and prepare the response
            String token = jwtUtils.generateTokenFromUsername(userDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", userDetails.getUsername());
            response.put("message", "Authentication successful");
            return ResponseEntity.ok(response);

        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws RuntimeException If the user is not found.
     */
    public Users getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Updates the details of an existing user.
     *
     * @param userId The ID of the user to update.
     * @param updatedUser The updated user details.
     * @throws RuntimeException If the user is not found.
     */
    @Transactional
    public void updateUser(String userId, Users updatedUser) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(user);
    }
}
package com.planto.user_service.controller;
/*
 * @author Praful
 */

import com.planto.user_service.entity.Users;
import com.planto.user_service.security.JwtUtils;
import com.planto.user_service.service.UserService;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller for managing user-related operations such as registration, login, profile management, and authentication checks.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Registers a new user.
     *
     * @param user The user details to register.
     * @return ResponseEntity containing the registered user and HTTP status.
     */
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        System.out.println("Inside the Controller");
        try {
            Users users = userService.registerUser(user);
            return ResponseEntity.ok(users);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param user The user credentials (username and password).
     * @return ResponseEntity containing the authentication result or error message.
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> authenticateUser(@RequestBody Users user) {
        return userService.authenticateUser(user.getUsername(), user.getPassword());
    }

    /**
     * Retrieves the profile of a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the user profile or an error message if not found.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ADMIN')")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") String userId) {
        try {
            System.out.println(userId);
            Users user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    /**
     * Updates the profile of an existing user.
     *
     * @param userId The ID of the user to update.
     * @param user The updated user details.
     * @return ResponseEntity containing a success message or an error message if the update fails.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> updateUserProfile(@PathVariable("id") String userId, @RequestBody Users user) {
        try {
            System.out.println();
            userService.updateUser(userId, user);
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Checks the authentication status of the current user.
     *
     * @return ResponseEntity containing the authentication object from the SecurityContext.
     */
    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

}
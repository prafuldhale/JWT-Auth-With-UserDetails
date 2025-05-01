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

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> authenticateUser(@RequestBody Users user) {
        return userService.authenticateUser(user.getUsername(), user.getPassword());
    }

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
    
    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

}

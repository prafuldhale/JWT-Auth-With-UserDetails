package com.planto.user_service.service;

/*
 * @author Praful
 */

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

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public Users registerUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Assuming user is enabled by default
        Authorities authorities = new Authorities(user.getUsername(), "ROLE_USER");
        authorityRepository.save(authorities);
        userRepository.save(user);
        return user;
    }

    public ResponseEntity<Object> authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // You can fetch additional details from the authenticated user if needed
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Example of returning a JWT token or other response
            String token =  jwtUtils.generateTokenFromUsername(userDetails);
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

    public Users getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void updateUser(String userId, Users updatedUser) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(user);
    }
}

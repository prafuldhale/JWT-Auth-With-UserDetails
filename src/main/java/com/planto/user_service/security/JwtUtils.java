package com.planto.user_service.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Utility class for handling JWT (JSON Web Token) operations such as token generation,
 * validation, and extraction of claims.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Generate JWT tokens for authenticated users.</li>
 *   <li>Validate JWT tokens for authenticity and expiration.</li>
 *   <li>Extract username and other claims from JWT tokens.</li>
 * </ul>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code jwtSecret} - The secret key used for signing JWT tokens.</li>
 *   <li>{@code jwtExpirationMs} - The expiration time for JWT tokens in milliseconds.</li>
 *   <li>{@code logger} - Logger instance for logging JWT-related operations.</li>
 * </ul>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li>{@code getJwtFromHeader(HttpServletRequest)} - Extracts the JWT token from the Authorization header.</li>
 *   <li>{@code generateTokenFromUsername(UserDetails)} - Generates a JWT token for a given user.</li>
 *   <li>{@code getUserNameFromJwtToken(String)} - Extracts the username from a JWT token.</li>
 *   <li>{@code validateJwtToken(String)} - Validates the authenticity and expiration of a JWT token.</li>
 *   <li>{@code key()} - Generates the signing key from the secret.</li>
 * </ul>
 *
 * @author Praful
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret; // Secret key for signing JWT tokens.

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs; // Expiration time for JWT tokens in milliseconds.

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request.
     *
     * @param request The HTTP request containing the Authorization header.
     * @return The JWT token if present and valid, otherwise null.
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove Bearer prefix.
        }
        return null;
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails The user details containing the username.
     * @return A signed JWT token.
     */
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    /**
     * Generates the signing key from the secret key.
     *
     * @return The signing key.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Validates the given JWT token for authenticity and expiration.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, otherwise false.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
package com.planto.user_service.response;

import java.util.List;

/**
 * Represents the response returned after a successful login.
 * This class contains the JWT token, username, and roles associated with the user.
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code jwtToken} - The JWT token generated for the authenticated user.</li>
 *   <li>{@code username} - The username of the authenticated user.</li>
 *   <li>{@code roles} - A list of roles assigned to the authenticated user.</li>
 * </ul>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li>Getters and setters for each field to access and modify their values.</li>
 * </ul>
 *
 * @author Praful
 */
public class LoginResponse {
    private String jwtToken; // The JWT token for the authenticated user.

    private String username; // The username of the authenticated user.
    private List<String> roles; // The roles assigned to the authenticated user.

    /**
     * Constructs a new LoginResponse with the specified username, roles, and JWT token.
     *
     * @param username The username of the authenticated user.
     * @param roles The roles assigned to the authenticated user.
     * @param jwtToken The JWT token generated for the authenticated user.
     */
    public LoginResponse(String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    /**
     * Retrieves the JWT token.
     *
     * @return The JWT token.
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Sets the JWT token.
     *
     * @param jwtToken The JWT token to set.
     */
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    /**
     * Retrieves the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the roles.
     *
     * @return A list of roles.
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles The roles to set.
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
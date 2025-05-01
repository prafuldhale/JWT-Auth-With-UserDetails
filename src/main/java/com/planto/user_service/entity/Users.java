package com.planto.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entity class representing a user in the system.
 * This class is mapped to a database table and includes fields for username, password, and account status.
 * It is used for authentication and user management.
 *
 * @author Praful
 */
@Entity
public class Users {

    /**
     * The username of the user.
     * This serves as the primary key and must be unique.
     */
    @Id
    @Column(nullable = false, length = 50)
    private String username;

    /**
     * The password of the user.
     * This is stored as a hashed value and must not exceed 500 characters.
     */
    @Column(nullable = false, length = 500)
    private String password;

    /**
     * Indicates whether the user's account is enabled.
     * A value of true means the account is active.
     */
    @Column(nullable = false)
    private boolean enabled;

    /**
     * Default constructor.
     * Initializes a new instance of the Users class.
     */
    public Users() {
    }

    /**
     * Parameterized constructor.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param enabled The account status of the user.
     */
    public Users(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user's account is enabled.
     *
     * @return True if the account is enabled, otherwise false.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the account status of the user.
     *
     * @param enabled The account status to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
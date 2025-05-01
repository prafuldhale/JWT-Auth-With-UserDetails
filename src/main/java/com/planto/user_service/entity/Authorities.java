package com.planto.user_service.entity;

import jakarta.persistence.*;

/**
 * Entity class representing authorities assigned to users.
 * This class is used to manage user roles and permissions.
 *
 * @author Praful
 */
@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username associated with the authority
    @Column(name = "username")
    private String user;

    // The authority or role assigned to the user
    @Column(nullable = false, length = 50)
    private String authority;

    /**
     * Default constructor.
     * Initializes the authority with a default value of "USER".
     */
    public Authorities() {
        this.authority = "USER"; // Default authority
    }

    /**
     * Parameterized constructor.
     *
     * @param user The username associated with the authority.
     * @param authority The authority or role assigned to the user.
     */
    public Authorities(String user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    /**
     * Gets the username associated with the authority.
     *
     * @return The username.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the username associated with the authority.
     *
     * @param user The username to set.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the authority or role assigned to the user.
     *
     * @return The authority.
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Sets the authority or role assigned to the user.
     *
     * @param authority The authority to set.
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
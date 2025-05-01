package com.planto.user_service.enums;

/**
 * Enum representing the roles available in the system.
 * These roles are used to define user permissions and access levels.
 *
 * <ul>
 *   <li>USER - Represents a standard user with limited access.</li>
 *   <li>ADMIN - Represents an administrator with elevated privileges.</li>
 * </ul>
 *
 * @author Praful
 */
public enum Role {
    /**
     * Standard user role with limited access.
     */
    USER,

    /**
     * Administrator role with elevated privileges.
     */
    ADMIN
}
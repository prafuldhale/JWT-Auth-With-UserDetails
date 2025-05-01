package com.planto.user_service.repository;

import com.planto.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Users entities.
 * This interface provides CRUD operations and query methods for the Users table.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 *
 * @see Users
 * @author Praful
 */
public interface UsersRepository extends JpaRepository<Users, String> {
}
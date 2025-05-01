package com.planto.user_service.repository;

import com.planto.user_service.entity.Authorities;
import com.planto.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Authorities entities.
 * This interface provides CRUD operations and query methods for the Authorities table.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 *
 * @author Praful
 */
public interface AuthorityRepository extends JpaRepository<Authorities, Long> {
}
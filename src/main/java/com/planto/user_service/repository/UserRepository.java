package com.planto.user_service.repository;

import java.util.Optional;

import com.planto.user_service.entity.UserDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing UserDetails entities.
 * This interface provides CRUD operations and query methods for the UserDetails table.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 *
 * <p>Custom query methods:</p>
 * <ul>
 *   <li>{@code findByUserId(Long userId)} - Retrieves a user along with their claims and nominees by user ID.</li>
 * </ul>
 *
 * @see UserDetails
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

	/**
	 * Finds a user by their user ID, including their claims and nominees.
	 * This method uses an EntityGraph to fetch related entities eagerly.
	 *
	 * @param userId The ID of the user to retrieve.
	 * @return An Optional containing the user details if found, otherwise empty.
	 */
	@EntityGraph(attributePaths = {"claims", "nominees"})
	Optional<UserDetails> findByUserId(Long userId);

}
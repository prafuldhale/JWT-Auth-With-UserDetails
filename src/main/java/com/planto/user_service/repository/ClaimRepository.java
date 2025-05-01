package com.planto.user_service.repository;

import java.util.List;

import com.planto.user_service.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Claim entities.
 * This interface provides CRUD operations and query methods for the Claim table.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 *
 * <p>Custom query methods:</p>
 * <ul>
 *   <li>{@code findByUserUserId(Long userId)} - Retrieves a list of claims associated with a specific user ID.</li>
 * </ul>
 *
 * @see Claim
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

	/**
	 * Finds all claims associated with a specific user ID.
	 *
	 * @param userId The ID of the user whose claims are to be retrieved.
	 * @return A list of claims associated with the given user ID.
	 */
	List<Claim> findByUserUserId(Long userId);

}
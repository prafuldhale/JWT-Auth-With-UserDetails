package com.planto.user_service.repository;

import java.util.List;

import com.planto.user_service.entity.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Nominee entities.
 * This interface provides CRUD operations and query methods for the Nominee table.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 *
 * <p>Custom query methods:</p>
 * <ul>
 *   <li>{@code findByUserUserId(Long userId)} - Retrieves a list of nominees associated with a specific user ID.</li>
 * </ul>
 *
 * @see Nominee
 */
@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long> {

	/**
	 * Finds all nominees associated with a specific user ID.
	 *
	 * @param userId The ID of the user whose nominees are to be retrieved.
	 * @return A list of nominees associated with the given user ID.
	 */
	List<Nominee> findByUserUserId(Long userId);

}
package com.planto.user_service.service;

import java.util.HashSet;
import java.util.List;

import com.planto.user_service.entity.Claim;
import com.planto.user_service.entity.Nominee;
import com.planto.user_service.entity.UserDetails;
import com.planto.user_service.entity.Users;
import com.planto.user_service.repository.ClaimRepository;
import com.planto.user_service.repository.NomineeRepository;
import com.planto.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * Service class for managing user-related operations.
 * This class provides methods for retrieving, adding, updating, and deleting user details,
 * along with associated claims and nominees.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Retrieve user details along with claims and nominees.</li>
 *   <li>Add a user with associated claims and nominees.</li>
 *   <li>Retrieve a user by ID or fetch all users.</li>
 *   <li>Save or delete user details.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@code UserRepository} - Repository for user data.</li>
 *   <li>{@code ClaimRepository} - Repository for user claims.</li>
 *   <li>{@code NomineeRepository} - Repository for user nominees.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service} - Marks this class as a Spring service component.</li>
 *   <li>{@code @Transactional} - Ensures transactional behavior for specific methods.</li>
 * </ul>
 */
@Service
public class UsersService {

	@Autowired
	private UserRepository userRepository; // Repository for user data.

	@Autowired
	private ClaimRepository claimRepository; // Repository for user claims.

	@Autowired
	private NomineeRepository nomineeRepository; // Repository for user nominees.

	/**
	 * Retrieves a user along with their associated claims and nominees.
	 *
	 * @param userId The ID of the user to retrieve.
	 * @return The user details including claims and nominees.
	 * @throws RuntimeException If the user is not found.
	 */
	public UserDetails getUserWithClaims(Long userId) {
		UserDetails user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
		List<Claim> claims = claimRepository.findByUserUserId(userId);
		List<Nominee> nominees = nomineeRepository.findByUserUserId(userId);
		user.setClaims(new HashSet<>(claims));
		user.setNominees(new HashSet<>(nominees));
		return user;
	}

	/**
	 * Adds a user along with their associated claims and nominees.
	 *
	 * @param user The user details to add.
	 * @return The saved user details.
	 */
	public UserDetails addUserWithDetails(UserDetails user) {
		if (user.getClaims() != null) {
			for (Claim claim : user.getClaims()) {
				claim.setUser(user);
			}
		}
		if (user.getNominees() != null) {
			for (Nominee nominee : user.getNominees()) {
				nominee.setUser(user);
			}
		}
		return userRepository.save(user);
	}

	/**
	 * Retrieves a user by their ID.
	 *
	 * @param id The ID of the user to retrieve.
	 * @return The user details, or null if not found.
	 */
	public UserDetails getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	/**
	 * Retrieves all users.
	 *
	 * @return A list of all user details.
	 */
	public List<UserDetails> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Saves the details of a user.
	 *
	 * @param user The user details to save.
	 * @return The saved user details.
	 */
	public UserDetails saveUser(UserDetails user) {
		return userRepository.save(user);
	}

	/**
	 * Deletes a user by their ID.
	 *
	 * @param id The ID of the user to delete.
	 */
	@Transactional
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
}
package com.planto.user_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class representing a nominee associated with a user.
 * This class is mapped to the "nominees" table in the database.
 */
@Entity
@Table(name = "nominees")
public class Nominee {

	/**
	 * Unique identifier for the nominee.
	 * This value is auto-generated.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nomineeId;

	/**
	 * The name of the nominee.
	 */
	private String name;

	/**
	 * The user associated with the nominee.
	 * This is a many-to-one relationship, with lazy fetching to optimize performance.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private UserDetails user;

	/**
	 * Gets the unique identifier of the nominee.
	 *
	 * @return The nominee ID.
	 */
	public Long getNomineeId() {
		return nomineeId;
	}

	/**
	 * Sets the unique identifier of the nominee.
	 *
	 * @param nomineeId The nominee ID to set.
	 */
	public void setNomineeId(Long nomineeId) {
		this.nomineeId = nomineeId;
	}

	/**
	 * Gets the name of the nominee.
	 *
	 * @return The name of the nominee.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the nominee.
	 *
	 * @param name The name of the nominee to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the user associated with the nominee.
	 *
	 * @return The user associated with the nominee.
	 */
	public UserDetails getUser() {
		return user;
	}

	/**
	 * Sets the user associated with the nominee.
	 *
	 * @param user The user to associate with the nominee.
	 */
	public void setUser(UserDetails user) {
		this.user = user;
	}
}
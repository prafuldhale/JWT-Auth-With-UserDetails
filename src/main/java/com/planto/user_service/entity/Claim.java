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
 * Entity class representing a claim made by a user.
 * This class is mapped to the "claims" table in the database.
 */
@Entity
@Table(name = "claims")
public class Claim {

	/**
	 * Unique identifier for the claim.
	 * This value is auto-generated.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long claimId;

	/**
	 * The amount associated with the claim.
	 */
	private Double claimAmount;

	/**
	 * The status of the claim (e.g., "Pending", "Approved", "Rejected").
	 */
	private String claimStatus;

	/**
	 * The user associated with the claim.
	 * This is a many-to-one relationship, with lazy fetching to optimize performance.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private UserDetails user;

	/**
	 * Gets the unique identifier of the claim.
	 *
	 * @return The claim ID.
	 */
	public Long getClaimId() {
		return claimId;
	}

	/**
	 * Sets the unique identifier of the claim.
	 *
	 * @param claimId The claim ID to set.
	 */
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	/**
	 * Gets the amount associated with the claim.
	 *
	 * @return The claim amount.
	 */
	public Double getClaimAmount() {
		return claimAmount;
	}

	/**
	 * Sets the amount associated with the claim.
	 *
	 * @param claimAmount The claim amount to set.
	 */
	public void setClaimAmount(Double claimAmount) {
		this.claimAmount = claimAmount;
	}

	/**
	 * Gets the status of the claim.
	 *
	 * @return The claim status.
	 */
	public String getClaimStatus() {
		return claimStatus;
	}

	/**
	 * Sets the status of the claim.
	 *
	 * @param claimStatus The claim status to set.
	 */
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	/**
	 * Gets the user associated with the claim.
	 *
	 * @return The user associated with the claim.
	 */
	public UserDetails getUser() {
		return user;
	}

	/**
	 * Sets the user associated with the claim.
	 *
	 * @param user The user to associate with the claim.
	 */
	public void setUser(UserDetails user) {
		this.user = user;
	}
}
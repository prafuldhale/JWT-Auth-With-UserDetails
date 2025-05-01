package com.planto.user_service.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity class representing user details.
 * This class is mapped to the "user" table in the database and includes fields for personal information,
 * address, and relationships with claims and nominees.
 */
@Entity
@Table(name = "user")
public class UserDetails {

	/**
	 * Unique identifier for the user.
	 * This value is auto-generated.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	/**
	 * The first name of the user.
	 */
	private String firstName;

	/**
	 * The last name of the user.
	 */
	private String lastName;

	/**
	 * The email address of the user.
	 */
	private String email;

	/**
	 * The city where the user resides.
	 */
	private String city;

	/**
	 * The state where the user resides.
	 */
	private String state;

	/**
	 * The pincode of the user's address.
	 */
	private int pincode;

	/**
	 * The full address of the user.
	 */
	private String address;

	/**
	 * The country of the user. Defaults to "India".
	 */
	private String county = "India";

	/**
	 * The gender of the user.
	 */
	private String gender;

	/**
	 * The age of the user.
	 */
	private int age;

	/**
	 * The marital status of the user.
	 */
	private String marital_status;

	/**
	 * The set of claims associated with the user.
	 * This is a one-to-many relationship, with cascade and orphan removal enabled.
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Claim> claims = new HashSet<>();

	/**
	 * The set of nominees associated with the user.
	 * This is a one-to-many relationship, with cascade and orphan removal enabled.
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Nominee> nominees = new HashSet<>();

	// Getters and setters

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Claim> getClaims() {
		return claims;
	}

	public void setClaims(Set<Claim> claims) {
		this.claims = claims;
	}

	public Set<Nominee> getNominees() {
		return nominees;
	}

	public void setNominees(Set<Nominee> nominees) {
		this.nominees = nominees;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return marital_status;
	}

	public void setMaritalStatus(String marital_status) {
		this.marital_status = marital_status;
	}
}
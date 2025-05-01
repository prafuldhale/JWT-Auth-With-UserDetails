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

@Entity
@Table(name="nominees")
public class Nominee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nomineeId;
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	@JsonBackReference
	
	private UserDetails user;
	public Long getNomineeId() {
		return nomineeId;
	}
	public void setNomineeId(Long nomineeId) {
		this.nomineeId = nomineeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	}
	
	

}

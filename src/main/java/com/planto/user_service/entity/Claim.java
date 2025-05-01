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
@Table(name= "claims")
public class Claim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long claimId;
private Double claimAmount;
private String claimStatus;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="user_id")
@JsonBackReference
private UserDetails user;
public Long getClaimId() {
	return claimId;
}
public void setClaimId(Long claimId) {
	this.claimId = claimId;
}
public Double getClaimAmount() {
	return claimAmount;
}
public void setClaimAmount(Double claimAmount) {
	this.claimAmount = claimAmount;
}
public String getClaimStatus() {
	return claimStatus;
}
public void setClaimStatus(String claimStatus) {
	this.claimStatus = claimStatus;
}
public UserDetails getUser() {
	return user;
}
public void setUser(UserDetails user) {
	this.user = user;
}

}

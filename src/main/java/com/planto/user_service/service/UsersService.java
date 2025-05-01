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

@Service
public class UsersService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClaimRepository claimRepository;
	@Autowired
	private NomineeRepository nomineeRepository;
	
	public UserDetails getUserWithClaims(Long userId) {
		UserDetails user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
		List<Claim> claims = claimRepository.findByUserUserId(userId);
		List<Nominee> nominees = nomineeRepository.findByUserUserId(userId);
		user.setClaims(new HashSet<>(claims));
		user.setNominees(new HashSet<>(nominees));
		return user;
	}
	
	public UserDetails addUserWithDetails(UserDetails user) {
		if(user.getClaims()!=null) {
			for(Claim claim : user.getClaims()) {
				claim.setUser(user);
			}
		}
		if(user.getNominees()!=null) {
			for(Nominee nominee : user.getNominees()) {
				nominee.setUser(user);
			}
		}
		
		return userRepository.save(user);
		}
	public UserDetails getUserById(Long id) {
	    return userRepository.findById(id).orElse(null);
	}
	
	public List<UserDetails> getAllUsers() {
	    return userRepository.findAll();
	}

	public UserDetails saveUser(UserDetails user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteUserById(Long id)
	{
		userRepository.deleteById(id);
	}
				
}




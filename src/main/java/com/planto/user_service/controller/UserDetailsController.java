package com.planto.user_service.controller;

import java.util.List;
import java.util.Optional;

import com.planto.user_service.entity.Claim;
import com.planto.user_service.entity.Nominee;
import com.planto.user_service.entity.UserDetails;
import com.planto.user_service.repository.UserRepository;
import com.planto.user_service.repository.UsersRepository;
import com.planto.user_service.service.UserService;
import com.planto.user_service.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/users/update")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserDetailsController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UsersService userService;
	
	@PostMapping("/add")
	public ResponseEntity<UserDetails> addUser(@RequestBody UserDetails user){
		UserDetails savedUser = userService.addUserWithDetails(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);	
	}
	
	@GetMapping("/{id}/claims")
	public ResponseEntity<UserDetails> getUserWithDetails(@PathVariable("id")Long id){
		UserDetails user = userService.getUserWithClaims(id);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserDetails>> getAllUsers() {
	    List<UserDetails> users = userService.getAllUsers();
	    return ResponseEntity.ok(users);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDetails> updateUser(@PathVariable("id") Long id, @RequestBody UserDetails userDetails) {
	    Optional <UserDetails> optionalUser = userRepository.findByUserId(id);
	    
	    if (!optionalUser.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	    UserDetails existingUser = optionalUser.get();
	 
	    // Update fields
	    existingUser.setFirstName(userDetails.getFirstName());
	    existingUser.setLastName(userDetails.getLastName());
	    existingUser.setEmail(userDetails.getEmail());
	    existingUser.setCity(userDetails.getCity());
	    existingUser.setAge(userDetails.getAge());
	 
	    // Update claims
	    existingUser.getClaims().clear();
	    if(userDetails.getClaims() != null) {
	    for (Claim claim : userDetails.getClaims()) {
	        claim.setUser(existingUser); // Set back-reference
	        existingUser.getClaims().add(claim);
	    }
	    }
	 
	    // Update nominees
	    existingUser.getNominees().clear();
	    if(userDetails.getNominees() != null) {
	    for (Nominee nominee : userDetails.getNominees()) {
	        nominee.setUser(existingUser); // Set back-reference
	        existingUser.getNominees().add(nominee);
	    }
	    }
	    UserDetails updatedUser = userService.saveUser(existingUser); // A method that just calls userRepository.save()
	    return ResponseEntity.ok(updatedUser);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletUser(@PathVariable("id") Long id){
		
		if(!userRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
			
		}
		userService.deleteUserById(id);
		return ResponseEntity.ok("User deleted successfully with ID: " +id);
	}
}

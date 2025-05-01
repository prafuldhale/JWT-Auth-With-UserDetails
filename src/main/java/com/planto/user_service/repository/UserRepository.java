package com.planto.user_service.repository;

import java.util.Optional;

import com.planto.user_service.entity.UserDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long>{
	
	@EntityGraph(attributePaths = {"claims", "nominees"})
	Optional <UserDetails> findByUserId(Long userId);


}

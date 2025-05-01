package com.planto.user_service.repository;

import java.util.List;

import com.planto.user_service.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>{
	List<Claim> findByUserUserId(Long userId);

}

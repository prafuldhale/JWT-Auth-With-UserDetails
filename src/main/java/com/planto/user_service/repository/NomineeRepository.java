package com.planto.user_service.repository;

import java.util.List;

import com.planto.user_service.entity.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long>{

	List<Nominee> findByUserUserId(Long userId);

}

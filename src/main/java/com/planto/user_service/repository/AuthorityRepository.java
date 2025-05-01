package com.planto.user_service.repository;

import com.planto.user_service.entity.Authorities;
import com.planto.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Praful
 */
public interface AuthorityRepository extends JpaRepository<Authorities, Long> {
}

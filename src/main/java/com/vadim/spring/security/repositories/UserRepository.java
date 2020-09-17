package com.vadim.spring.security.repositories;

import com.vadim.spring.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
}

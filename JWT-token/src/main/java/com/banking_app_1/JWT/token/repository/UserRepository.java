package com.banking_app_1.JWT.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking_app_1.JWT.token.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String userName);
}

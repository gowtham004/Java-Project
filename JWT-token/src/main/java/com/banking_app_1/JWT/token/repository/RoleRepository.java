package com.banking_app_1.JWT.token.repository;

import com.banking_app_1.JWT.token.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long>{

    Optional<Role> findByName(String name);
}

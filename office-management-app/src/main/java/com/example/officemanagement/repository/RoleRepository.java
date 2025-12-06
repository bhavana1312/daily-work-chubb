package com.example.officemanagement.repository;

import com.example.officemanagement.model.ERole;
import com.example.officemanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}

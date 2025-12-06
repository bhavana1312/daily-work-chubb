package com.example.officemanagement.repository;

import com.example.officemanagement.model.Employee;
import com.example.officemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByUser(User user);
	List<Employee> findByManagerUsername(String managerUsername);
}

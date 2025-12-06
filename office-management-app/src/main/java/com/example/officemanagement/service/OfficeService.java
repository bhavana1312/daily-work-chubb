package com.example.officemanagement.service;

import com.example.officemanagement.model.Employee;
import com.example.officemanagement.model.User;
import com.example.officemanagement.repository.EmployeeRepository;
import com.example.officemanagement.repository.UserRepository;
import com.example.officemanagement.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OfficeService {

	private final EmployeeRepository employeeRepository;
	private final UserRepository userRepository;

	public ResponseEntity<?> getAllEmployees() {
		return ResponseEntity.ok(employeeRepository.findAll());
	}

	public ResponseEntity<?> getEmployeeProfile(Authentication authentication) {
		UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsername(principal.getUsername()).orElseThrow();

		Employee emp = employeeRepository.findByUser(user).orElse(null);

		if (emp == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(emp);
	}

	public ResponseEntity<?> getMyAccount(Authentication authentication) {
		UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsername(principal.getUsername()).orElseThrow();

		Employee emp = employeeRepository.findByUser(user).orElse(null);

		if (emp == null) {
			Map<String, Object> hrInfo = new HashMap<>();
			hrInfo.put("id", user.getId());
			hrInfo.put("username", user.getUsername());
			hrInfo.put("email", user.getEmail());
			hrInfo.put("roles", user.getRoles());
			hrInfo.put("message", "You are an HR. No employee profile exists.");
			return ResponseEntity.ok(hrInfo);
		}

		return ResponseEntity.ok(emp);
	}

	public String systemStatus() {
		return "Office Management System API is up";
	}
	
	public ResponseEntity<?> getEmployeesUnderHr(Authentication authentication) {

	    UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
	    String hrUsername = principal.getUsername();

	    List<Employee> employees = employeeRepository.findByManagerUsername(hrUsername);

	    return ResponseEntity.ok(employees);
	}

}

package com.example.officemanagement.service;

import com.example.officemanagement.dto.JwtResponse;
import com.example.officemanagement.dto.LoginRequest;
import com.example.officemanagement.dto.MessageResponse;
import com.example.officemanagement.dto.SignupRequest;
import com.example.officemanagement.model.ERole;
import com.example.officemanagement.model.Employee;
import com.example.officemanagement.model.Role;
import com.example.officemanagement.model.User;
import com.example.officemanagement.repository.EmployeeRepository;
import com.example.officemanagement.repository.RoleRepository;
import com.example.officemanagement.repository.UserRepository;
import com.example.officemanagement.security.UserDetailsImpl;
import com.example.officemanagement.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;

	public ResponseEntity<?> login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles));
	}

	public ResponseEntity<?> signup(SignupRequest req) {

		if (userRepository.existsByUsername(req.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already taken"));
		}

		if (userRepository.existsByEmail(req.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already used"));
		}

		User user = new User();
		user.setUsername(req.getUsername());
		user.setEmail(req.getEmail());
		user.setPassword(encoder.encode(req.getPassword()));

		Set<Role> roles = new HashSet<>();
		boolean isEmployee = true;

		if (req.getRoles() == null || req.getRoles().isEmpty()) {
			roles.add(roleRepository.findByName(ERole.ROLE_EMPLOYEE).orElseThrow());
		} else {
			for (String roleName : req.getRoles()) {
				if (roleName.equalsIgnoreCase("hr")) {
					roles.add(roleRepository.findByName(ERole.ROLE_HR).orElseThrow());
					isEmployee = false;
				} else {
					roles.add(roleRepository.findByName(ERole.ROLE_EMPLOYEE).orElseThrow());
				}
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		if (isEmployee) {

			List<User> allHrs = userRepository.findAll().stream()
					.filter(u -> u.getRoles().stream().anyMatch(r -> r.getName().equals(ERole.ROLE_HR))).toList();

			if (allHrs.isEmpty()) {
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Cannot register employee. No HRs found in the system."));
			}

			User randomHr = allHrs.get(new Random().nextInt(allHrs.size()));

			Employee emp = new Employee();
			emp.setFullName(req.getUsername());
			emp.setPosition("Not Assigned");
			emp.setDepartment("Not Assigned");
			emp.setManagerUsername(randomHr.getUsername());
			emp.setUser(user);

			employeeRepository.save(emp);
		}

		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
	}

	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new MessageResponse("Signed out. Delete your JWT token from client."));
	}
}

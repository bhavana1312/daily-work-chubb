package com.example.officemanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.officemanagement.service.OfficeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
@CrossOrigin
public class OfficeController {

	private final OfficeService officeService;
	
	@GetMapping("/hr/my-employees")
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<?> getEmployeesUnderMyControl(Authentication authentication) {
	    return officeService.getEmployeesUnderHr(authentication);
	}


	@GetMapping("/employee/me")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<?> getMyProfile(Authentication authentication) {
		return officeService.getEmployeeProfile(authentication);
	}

	@GetMapping("/dashboard")
	public ResponseEntity<String> publicInfo() {
		return ResponseEntity.ok(officeService.systemStatus());
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyAccount(Authentication authentication) {
		return officeService.getMyAccount(authentication);
	}
}

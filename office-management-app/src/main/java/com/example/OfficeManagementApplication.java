package com.example;

import com.example.officemanagement.model.ERole;
import com.example.officemanagement.model.Role;
import com.example.officemanagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class OfficeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficeManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {

			if (roleRepository.count() == 0) {
				roleRepository.save(new Role(null, ERole.ROLE_HR));
				roleRepository.save(new Role(null, ERole.ROLE_EMPLOYEE));
			}

		};
	}
}

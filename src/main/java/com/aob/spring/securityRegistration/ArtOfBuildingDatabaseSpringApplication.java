package com.aob.spring.securityRegistration;

import com.aob.spring.securityRegistration.repository.RoleRepository;
import com.aob.spring.securityRegistration.repository.model.Role;
import com.aob.spring.securityRegistration.repository.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ArtOfBuildingDatabaseSpringApplication implements ApplicationRunner {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ArtOfBuildingDatabaseSpringApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Arrays
				.stream(UserType.values())
				.forEach(userType -> {
					if (roleRepository.findByRole(userType.name()) == null) {
						roleRepository.save(new Role(userType.name()));
					}
				});

	}
}

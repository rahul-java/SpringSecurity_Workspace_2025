package com.app.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.security.entity.User;
import com.app.security.repo.UserRepository;

@SpringBootApplication
public class SpringSecurityProjectUsingDbApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityProjectUsingDbApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		User user = userRepository.findByUsername("substring").orElse(null);
		
		if(user==null) {
			user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setUsername("substring");
			user.setPassword(passwordEncoder.encode("substring123"));
			user.setRole("USER");
			userRepository.save(user);
			System.out.println("User is created...");
		}
	}

}

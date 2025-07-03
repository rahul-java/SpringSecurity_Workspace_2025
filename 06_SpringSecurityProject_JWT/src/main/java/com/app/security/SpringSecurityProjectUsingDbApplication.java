package com.app.security;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.security.entity.Role;
import com.app.security.entity.User;
import com.app.security.repo.RoleRepository;
import com.app.security.repo.UserRepository;

@SpringBootApplication
public class SpringSecurityProjectUsingDbApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityProjectUsingDbApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		Role role1 = roleRepository.findByName("ROLE_ADMIN").orElse(null);		
		Role role2 = roleRepository.findByName("ROLE_GUEST").orElse(null);		
		if(role1==null) {
			//ROLE_ADMIN
			role1 = new Role();
			role1.setName("ROLE_ADMIN");
			role1.setRoleId(UUID.randomUUID().toString());
			roleRepository.save(role1);
		}
		if(role2==null) {
			//ROLE_GUEST
			role2 = new Role();
			role2.setName("ROLE_GUEST");
			role2.setRoleId(UUID.randomUUID().toString());
			roleRepository.save(role2);
		}
		
		User user = userRepository.findByUsername("ram").orElse(null);		
		if(user==null) {
			user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setUsername("ram");
			user.setPassword(passwordEncoder.encode("ram123"));
			user.setRoles(List.of(role1,role2));
			userRepository.save(user);
			System.out.println("User is created..."+user.getUsername());
			
		}
		User user2 = userRepository.findByUsername("shyam").orElse(null);		
		if(user2==null) {
			user2 = new User();
			user2.setUserId(UUID.randomUUID().toString());
			user2.setUsername("shyam");
			user2.setPassword(passwordEncoder.encode("shyam123"));
			user2.setRoles(List.of(role2));
			userRepository.save(user2);
			System.out.println("User is created..."+user2.getUsername());
			
		}
	}

}

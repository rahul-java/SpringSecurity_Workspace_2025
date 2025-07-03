package com.app.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.security.config.JwtHelper;
import com.app.security.entity.User;
import com.app.security.repo.UserRepository;


@SpringBootTest
class SpringSecurityProjectUsingDbApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtHelper jwtHelper;

	@Test
	void contextLoads() {
	}



	@Test
	void testToken(){

		User user = userRepository.findByUsername("ram").get();

		String token= jwtHelper.generateToken(user);
		System.out.println(token);

		System.out.println("getting username from token");

		System.out.println(jwtHelper.getUsernameFromToken(token));

		System.out.println("Is Token expired: ");
		System.out.println(jwtHelper.isTokenExpired(token));


	}

}

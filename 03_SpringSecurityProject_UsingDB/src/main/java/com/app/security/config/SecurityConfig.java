package com.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

	/*
    @Bean
    public UserDetailsService userDetailsService(){

        //Without using db (InMemory)
        UserDetails user1 = User.withDefaultPasswordEncoder().username("rahul").password("rahul@123").roles("ADMIN","GUEST").build();
        UserDetails user2 = User.withDefaultPasswordEncoder().username("rohit").password("rohit@123").roles("ADMIN").build();
        InMemoryUserDetailsManager inMemoryUserDetailsService =new InMemoryUserDetailsManager(user1,user2);
        return inMemoryUserDetailsService;

    }
    */
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

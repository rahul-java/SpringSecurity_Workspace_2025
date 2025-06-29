package com.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		//configuration
		/*
		httpSecurity.authorizeHttpRequests(request->{
			request.requestMatchers("/api/route2","/api/register").permitAll(); //public url
			request.requestMatchers("/users/**").permitAll(); //jo bhi url /users/ se start hoge wo public h
			request.requestMatchers(HttpMethod.POST,"/products");  //post methods of /products will be authenticatedS
			request.anyRequest().authenticated(); //authentication required
		});
		*/
		
		/*
		httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/api/route2").permitAll()
				.requestMatchers("/users/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/products").authenticated()
				.anyRequest().authenticated());
		*/
		
		httpSecurity.authorizeHttpRequests(request->request.anyRequest().permitAll());
		
		
		httpSecurity.formLogin(Customizer.withDefaults()); //for browser
		httpSecurity.httpBasic(Customizer.withDefaults()); // for post man testing username pwd by url
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

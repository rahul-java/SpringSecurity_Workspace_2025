package com.app.security.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.app.security.constant.AppConstants;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter filter;
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	
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
		
		//httpSecurity.authorizeHttpRequests(request->request.anyRequest().permitAll());
		
		//disable cors and csrf 
		//httpSecurity.cors(httpSecurityCorsConfigurer->httpSecurityCorsConfigurer.disable());
		httpSecurity.cors(httpSecurityCorsConfigurer->httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource(){

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				//single origin : this will allow all the controllers to localhost:4200
				//corsConfiguration.addAllowedOrigin("http://localhost:4200");
				//for multiple origins
				//corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200","http://localhost:5200"));
				// for all origins from anywhere
				//corsConfiguration.setAllowedOrigins(List.of("*"));
				corsConfiguration.setAllowedOriginPatterns(List.of("*"));
				corsConfiguration.setAllowedMethods(List.of("*"));
				corsConfiguration.setAllowCredentials(true);
				corsConfiguration.setAllowedHeaders(List.of("*"));
				corsConfiguration.setMaxAge(3000L);
				return corsConfiguration;
			}
			
		}));
		httpSecurity.csrf(httpSecurityCsrfConfigurer->httpSecurityCsrfConfigurer.disable());
		
		httpSecurity.authorizeHttpRequests(
				request->request.requestMatchers("/api/route3","/api/route4").hasRole(AppConstants.ROLE_GUEST)
				                .requestMatchers("/api/route1","/api/route2").hasRole("ADMIN")
				                .requestMatchers(HttpMethod.POST,"/auth/generate-token").permitAll()
				                .requestMatchers("/auth/**").authenticated()
				                .anyRequest().permitAll()
				);
		
		
		httpSecurity.formLogin(Customizer.withDefaults()); //for browser
		//httpSecurity.httpBasic(Customizer.withDefaults()); // for post man testing username pwd by url
		
		httpSecurity.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint));
		httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
}

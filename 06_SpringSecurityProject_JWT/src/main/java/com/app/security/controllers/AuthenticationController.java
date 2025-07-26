package com.app.security.controllers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.config.JwtHelper;
import com.app.security.dtos.JwtRequest;
import com.app.security.dtos.JwtResponse;
import com.app.security.dtos.UserDto;
import com.app.security.entity.User;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private Logger logger=LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
		logger.info("Username {} , Password {}",request.getUsername(),request.getPassword());
		
		this.doAuthenticate(request.getUsername(),request.getPassword());
		
		User user = (User)userDetailsService.loadUserByUsername(request.getUsername());
		//generate token
		String token = jwtHelper.generateToken(user);
		//send response
		JwtResponse jwtResponse = JwtResponse.builder().token(token).userDto(modelMapper.map(user, UserDto.class)).build();
		
		return ResponseEntity.ok(jwtResponse);
	}

	private void doAuthenticate(String username, String password) {
		
		try {
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
			authenticationManager.authenticate(authenticationToken);
			
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username and password !!");
		}
		
	}
}

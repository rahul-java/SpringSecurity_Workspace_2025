package com.app.security.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private Logger logger=LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Authorization : Bearer token:shjhfjcffhfhfkksfsf
		String requestHeader = request.getHeader("Authorization");
		logger.info("Header {} ",requestHeader);
		
		String username=null;
		String token=null;
		
		if(requestHeader!=null && requestHeader.startsWith("Bearer"))
		{
			token=requestHeader.substring(7);
			try {
				username=jwtHelper.getUsernameFromToken(token);
				logger.info("Token sername : {}",username);
			} catch (IllegalArgumentException e) {
				
				logger.info("llegal Argument xception while fething the username {}",e.getMessage());
			} catch (ExpiredJwtException e) {
				
				logger.info("Given Jwt token is expired : {}",e.getMessage());
			} catch (MalformedJwtException e) {
				
				logger.info("Some changes has done in token : {}",e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			logger.info("Invalid Header !! Header is not starting with 'Bearer' ");
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if(username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token)) {
				
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

}

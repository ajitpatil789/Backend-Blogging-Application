package com.ajit.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajit.blog.payloads.JwtAuthRequest;
import com.ajit.blog.payloads.JwtAuthResponce;
import com.ajit.blog.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponce> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		System.out.println("Step 1 :");
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponce responce = new JwtAuthResponce();
		responce.setToken(token);
		return new ResponseEntity<JwtAuthResponce>(responce,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		System.out.println("Step 2 :");
		System.out.println("Username: "+username);
		System.out.println("Password :"+password);
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
			
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			
			throw new Exception("Invalid username and password");
		}
		
		
	}

}

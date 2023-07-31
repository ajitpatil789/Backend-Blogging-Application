package com.ajit.blog.controller;

import org.modelmapper.ModelMapper;
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

import com.ajit.blog.entities.User;
import com.ajit.blog.exceptions.ApiException;
import com.ajit.blog.payloads.JwtAuthRequest;
import com.ajit.blog.payloads.JwtAuthResponce;
import com.ajit.blog.payloads.UserDto;
import com.ajit.blog.security.JwtTokenHelper;
import com.ajit.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponce> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponce responce = new JwtAuthResponce();
		responce.setToken(token);
		responce.setUser(this.mapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponce>(responce,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
			
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			
			throw new ApiException("Invalid username or password");
		}
		
		
	}
	
	//  Register New User API
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registeredUser = this.userService.RegisterNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
		
	}
	
	

}

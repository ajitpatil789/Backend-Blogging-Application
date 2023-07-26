package com.ajit.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ajit.blog.entities.User;
import com.ajit.blog.exceptions.ResourceNotFoundException;
import com.ajit.blog.repository.UserRepo;

// UserDetailsService used for database authentication
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by username
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user", "Email:"+username, 0));
		return user;
	}

}

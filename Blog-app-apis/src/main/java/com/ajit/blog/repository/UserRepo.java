package com.ajit.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajit.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);

}

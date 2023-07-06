package com.ajit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajit.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}

package com.ajit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajit.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}

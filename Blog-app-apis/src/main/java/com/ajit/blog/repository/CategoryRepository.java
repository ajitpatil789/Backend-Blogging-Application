package com.ajit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajit.blog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}

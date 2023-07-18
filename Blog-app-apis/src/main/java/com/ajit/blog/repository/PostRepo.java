package com.ajit.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajit.blog.entities.Category;
import com.ajit.blog.entities.Post;
import com.ajit.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
	


}

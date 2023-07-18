package com.ajit.blog.services;

import java.util.List;

import com.ajit.blog.entities.Post;
import com.ajit.blog.payloads.PostDto;
import com.ajit.blog.payloads.PostResponce;

public interface PostService {
	
	// Create Post
	
	PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);
	
	// Update Post
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// Delete Post
	
	void deletePost(Integer postId);
	
	//Get All Posts
	
	PostResponce getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	// Get Single  Post
	
	PostDto getPostById(Integer postId);
	
	
	// Get All Posts By Category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// Get All Posts By User
	
	List<PostDto> getPostByUser(Integer userId);
	
	// Search Posts
	
	List<PostDto> searchPost(String keyword);
	

}

package com.ajit.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajit.blog.config.AppConstants;
import com.ajit.blog.payloads.ApiResponce;
import com.ajit.blog.payloads.PostDto;
import com.ajit.blog.payloads.PostResponce;
import com.ajit.blog.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//Post- Create Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@ Valid @RequestBody PostDto dto, 
			@PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(dto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	// Get Posts By User ID
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK); 
		
		
	}
	
	//Get Posts by Category ID
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	//Get All the Posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponce>getAllPosts(
				@RequestParam(value = "pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
				@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
				@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
				@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			){
		PostResponce allPosts = this.postService.getAllPosts(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponce>(allPosts,HttpStatus.OK);
	}
	
	// Get posts by Post ID
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		
	}
	//Update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
		
	}
	
	
	// Delete Post
	@DeleteMapping("/posts/{postId}")
	public ApiResponce deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponce("Post deleted Successfully...!",true);
	}
	
	//Search Post
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keywords") String keywords){
		List<PostDto> searchPost = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
		
	}

}

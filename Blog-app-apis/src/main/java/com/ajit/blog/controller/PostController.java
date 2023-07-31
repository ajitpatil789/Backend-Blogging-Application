package com.ajit.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ajit.blog.config.AppConstants;
import com.ajit.blog.payloads.ApiResponce;
import com.ajit.blog.payloads.PostDto;
import com.ajit.blog.payloads.PostResponce;
import com.ajit.blog.services.FileService;
import com.ajit.blog.services.PostService;

@RestController
@RequestMapping("/api/v1/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
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
	
	// Upload Post Image
	@PostMapping("posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
				
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	// Method to serve file
		@GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable String imageName,HttpServletResponse responce) throws IOException {
			
			InputStream resource = this.fileService.getResource(path, imageName);
			responce.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, responce.getOutputStream());
			
		}

}

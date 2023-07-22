package com.ajit.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajit.blog.payloads.ApiResponce;
import com.ajit.blog.payloads.CommentDto;
import com.ajit.blog.services.CommentService;



@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	// Create Post Controller
	
	@PostMapping("user/{userId}/posts/{postId}/comment")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId, @PathVariable Integer userId){
		CommentDto comment = this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.CREATED);
		
	}
	
	// Delete Post
	@DeleteMapping("/comment/{commentId}")
	public ApiResponce deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ApiResponce("Comment Deleted...!", true);
		
	}
}

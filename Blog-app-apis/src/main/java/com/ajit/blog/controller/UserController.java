package com.ajit.blog.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ajit.blog.payloads.ApiResponce;
import com.ajit.blog.payloads.UserDto;
import com.ajit.blog.services.UserService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	// POST - Create user

	@PostMapping("/")
	public ResponseEntity<UserDto> creatUser(@RequestBody @Valid UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}

	// PUT-Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable() Integer userId) {
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUser);

	}
	
	// GET- Get all Users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>>getAllUser(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	// GET Single USer
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getSingleUser(@PathVariable Integer userId){
		UserDto userById = this.userService.getUserById(userId);
		return ResponseEntity.ok(userById);
	}
	
	//admin
	//DELETE -Delete User
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponce>deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("User delete successfully", true),HttpStatus.OK);
		
	}

}

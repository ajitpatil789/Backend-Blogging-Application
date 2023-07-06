package com.ajit.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajit.blog.entities.User;
import com.ajit.blog.exceptions.ResourceNotFoundException;
import com.ajit.blog.payloads.UserDto;
import com.ajit.blog.repository.UserRepo;
import com.ajit.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//New User Creation
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	// User Update
	@Override
	public UserDto updateUser(UserDto userDto, Integer UserId) {
		User user = this.userRepo.findById(UserId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",UserId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser= this.userRepo.save(user);  
		UserDto userDto2 = this.userToDto(updatedUser);
		return userDto2;
	}

	//Get user by ID
	@Override
	public UserDto getUserById(Integer UserId) {
		User user = this.userRepo.findById(UserId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",UserId));
		return this.userToDto(user);
	}

	//Get all users
	@Override
	public List<UserDto> getAllUsers() {
		List<User>users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	// Delete User
	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
		this.userRepo.delete(user); 
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
		
		
		
	} 
		


}

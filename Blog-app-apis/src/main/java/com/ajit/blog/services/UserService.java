package com.ajit.blog.services;

import java.util.List;
import com.ajit.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer UserId);
	UserDto getUserById(Integer UserId);
	List<UserDto> getAllUsers(); 
	void deleteUser(Integer userId);
}

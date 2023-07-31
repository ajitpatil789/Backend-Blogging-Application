package com.ajit.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthResponce {
	
	private String token;
	
	private UserDto user;

}

package com.ajit.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter

public class UserDto {
	
	
	private int id;
	
	@NotEmpty
	@Size(min = 4 ,message = "Username must have atleast 4 characters !!")
	private String name;
	
	@Email(message = "Enter valid email address")
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(min = 4,max = 10, message = "Password must have min 4 charaters and max charater 10")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<CommentDto>comments = new HashSet<>();

}

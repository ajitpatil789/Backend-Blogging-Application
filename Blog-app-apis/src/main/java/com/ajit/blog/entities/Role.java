package com.ajit.blog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id
	int id;
	
	String name;

}

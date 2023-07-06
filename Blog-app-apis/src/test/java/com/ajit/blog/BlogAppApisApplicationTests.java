package com.ajit.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ajit.blog.repository.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {
	
	@Autowired
	private UserRepo repo;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest() {
		String className = this.repo.getClass().getName();
		String packageName = this.repo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}

}

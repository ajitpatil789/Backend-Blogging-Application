package com.ajit.blog.services;

import java.util.List;

import com.ajit.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	 CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryID);
	
	//delete
	 void deleteCategory(Integer categoryID);
	
	//get
	
	 CategoryDto getCategory(Integer categoryId);
	
	//getAll
	
	 List<CategoryDto> getCategories();

}

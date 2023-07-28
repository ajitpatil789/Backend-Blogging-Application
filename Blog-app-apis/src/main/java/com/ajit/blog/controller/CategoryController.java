package com.ajit.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajit.blog.payloads.ApiResponce;
import com.ajit.blog.payloads.CategoryDto;
import com.ajit.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	// POST - create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
	}
	
	//put -update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable() Integer categoryId){
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updateCategory);
	}
	
	//GET all categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getAllCategory(){
		return ResponseEntity.ok(this.categoryService.getCategories());
		
	}
	
	//get user by ID
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer categoryId){
		CategoryDto category = this.categoryService.getCategory(categoryId);
		return ResponseEntity.ok(category);
		
	}
	
	//DELETE category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponce>deleteCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("Category delete successfully", true),HttpStatus.OK);
		
	}
	

}

package com.ajit.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ajit.blog.entities.Category;
import com.ajit.blog.exceptions.ResourceNotFoundException;
import com.ajit.blog.payloads.CategoryDto;
import com.ajit.blog.repository.CategoryRepository;
import com.ajit.blog.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	// Create Category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.dtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepository.save(category);
		return this.categoryToDto(savedCategory);
	}
	
	// Update Category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryID) {
		Category category = this.categoryRepository.findById(categoryID)
			.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryID));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCategory = this.categoryRepository.save(category);
		return this.categoryToDto(updateCategory);
	}
	
	// delete category
	@Override
	public void deleteCategory(Integer categoryID) {
		Category category = this.categoryRepository.findById(categoryID)
			.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryID));
		this.categoryRepository.delete(category);

	}

	//get category by ID
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
			.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		return this.categoryToDto(category);
				
	}
	
	//get All categories
	@Override
	public List<CategoryDto> getCategories(CategoryDto categoryDto) {
		List<Category> findAll = this.categoryRepository.findAll();
		List<CategoryDto> collect = findAll.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
		return collect;
	}
	
	private Category dtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	private CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}

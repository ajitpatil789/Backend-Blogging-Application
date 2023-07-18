package com.ajit.blog.services.implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ajit.blog.entities.Category;
import com.ajit.blog.entities.Post;
import com.ajit.blog.entities.User;
import com.ajit.blog.exceptions.ResourceNotFoundException;
import com.ajit.blog.payloads.PostDto;
import com.ajit.blog.payloads.PostResponce;
import com.ajit.blog.repository.CategoryRepository;
import com.ajit.blog.repository.PostRepo;
import com.ajit.blog.repository.UserRepo;
import com.ajit.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		// convert postdto to post by using model mapper
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post);
		
		// Return PostDTO
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponce getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
//		Sort sort = null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort =Sort.by(sortBy).ascending();
//		}else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		// above logic using ternary operation
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		
		Pageable p = PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		//List<Post> posts = this.postRepo.findAll();
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce =new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}
 
	@Override
	public PostDto getPostById(Integer postId) {
		Post posts = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post ID", postId));
	
		return this.modelMapper.map(posts, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post) ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}

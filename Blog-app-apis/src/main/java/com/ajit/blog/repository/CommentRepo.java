package com.ajit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajit.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}

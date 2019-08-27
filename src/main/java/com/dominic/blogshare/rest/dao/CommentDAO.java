package com.dominic.blogshare.rest.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dominic.blogshare.core.Comment;

@Repository 
public interface CommentDAO extends JpaRepository<Comment, Long> {
  Collection<Comment> findByParentIdIsNull();
  Collection<Comment> findByParentIdIsNotNull();
}

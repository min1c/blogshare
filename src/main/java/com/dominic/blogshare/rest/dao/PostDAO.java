package com.dominic.blogshare.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dominic.blogshare.core.Post;

@Repository 
public interface PostDAO extends JpaRepository<Post, Long> {
}

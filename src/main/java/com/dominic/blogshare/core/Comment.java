package com.dominic.blogshare.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = true)
    private Long parentId;
    
    @Column(nullable = true)
    private Long postId;
    
    public Long getId() {
      return id;
    }
    
    public void setId(Long id) {
      this.id = id;
    }
    
    public String getContent() {
      return content;
    }
    
    public void setContent(String content) {
      this.content = content;
    }
    
    public Long getParentId() {
      return parentId;
    }
    
    public void setParentId(Long parentId) {
      this.parentId = parentId;
    }

    public Long getPostId() {
      return postId;
    }
    
    public void setPostId(Long postId) {
      this.postId = postId;
    }
    

}

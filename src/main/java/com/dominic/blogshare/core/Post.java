package com.dominic.blogshare.core;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    
    @Column
    private String title;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
    
    public String getTitle()
    {
      return title;
    }

    public void setTitle(String title)
    {
      this.title = title;
    }

    @Transient
    private List<Comment> comments;
    
    public Long getId() {
      return id;
    }
    
    public void setId(Long id) {
      this.id = id;
    }
    
    public List<Comment> getComments()
    {
      if (comments == null)
        comments = new ArrayList<Comment>();
      return comments;
    }
}

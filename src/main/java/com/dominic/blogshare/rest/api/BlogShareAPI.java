package com.dominic.blogshare.rest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dominic.blogshare.core.Comment;
import com.dominic.blogshare.core.Post;
import com.dominic.blogshare.rest.ApiError;
import com.dominic.blogshare.rest.dao.CommentDAO;
import com.dominic.blogshare.rest.dao.PostDAO;
import com.dominic.blogshare.rest.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class BlogShareAPI {

  @Autowired
  private CommentDAO commentDAO;
  @Autowired
  private PostDAO postDAO;
    
  @GetMapping(path="/comment")
	public @ResponseBody Iterable<Comment> getAllComments(@RequestParam(required=false) boolean getReplies) 
  {
    if (getReplies)
    {
      return commentDAO.findAll();
    }
		return commentDAO.findByParentIdIsNull();
	}
  
  @GetMapping(path="/post")
  public @ResponseBody Iterable<Post> getAllPosts(@RequestParam(required=false) boolean getComments) 
  {
    List<Post> allPosts = postDAO.findAll();
    
    if (getComments)
    {
      for (Post post : allPosts)
      {
        Comment comment = new Comment();
        comment.setPostId(post.getId());
        Example<Comment> query = Example.of(comment);
        post.getComments().addAll(commentDAO.findAll(query));
      }
    }
    
    return allPosts;
  }

  @GetMapping(path="/post/{id}")
  public @ResponseBody Post getPost(@PathVariable() Long id, @RequestParam(required=false) boolean getComments) 
  {
    Post post = postDAO.findById(id).get();

    if (getComments)
    {
      Comment comment = new Comment();
      comment.setPostId(post.getId());
      Example<Comment> query = Example.of(comment);
      post.getComments().addAll(commentDAO.findAll(query));
    }
    
    return post;
  }
    
  @PostMapping(path="/comment")
	public @ResponseBody ResponseEntity<Object> postComment(@RequestBody Comment comment) 
  {
    String content = comment.getContent();
    Long parentId = comment.getParentId();
    Long postId = comment.getPostId();
    
    Comment search = new Comment();
    search.setParentId(parentId);
    search.setPostId(postId);
    
    if (parentId != null && !commentDAO.findById(parentId).isPresent())
    {
      ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, 
          "Comment with Id " + parentId + " does not exist.", new Exception());
      return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    
    if (postId != null && !postDAO.findById(postId).isPresent())
    {
      ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, 
          "Post with Id " + postId + " does not exist.", new Exception());
      return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    
    Comment test = new Comment();
  	test.setContent(content);
  	test.setParentId(parentId);
    test.setPostId(postId);
  	commentDAO.save(test);
  	return new ResponseEntity<Object>(test, HttpStatus.CREATED);
	}

  @PostMapping(path="/post")
  public @ResponseBody ResponseEntity<Object> postPost(@RequestBody Post post) 
  {
    postDAO.save(post);
    return new ResponseEntity<Object>(post, HttpStatus.CREATED);
  }
    
  @DeleteMapping(path="/comment/{id}")
  public @ResponseBody List<Comment> deleteComment(@PathVariable(value = "id") Long id)
  {
    List<Comment> deleted = new ArrayList<Comment>();
    
    Comment comment = commentDAO.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

    Comment search = new Comment();
    search.setParentId(comment.getId());
    Example<Comment> query = Example.of(search);

    for (Comment child : commentDAO.findAll(query))
    {
      deleted.addAll(deleteComment(child.getId()));
    }
    
    commentDAO.delete(comment);
    deleted.add(comment);
    
    return deleted;
  }

}

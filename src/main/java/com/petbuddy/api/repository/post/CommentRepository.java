package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.post.Comment;
import com.petbuddy.api.model.post.Post;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

  Comment insert(Comment comment);

  void update(Comment comment);

  Optional<Comment> findById(Id<Comment, Long> commentId);

  List<Comment> findAll(Id<Post, Long> postId);

}
package com.petbuddy.api.service.post;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.post.Comment;
import com.petbuddy.api.model.post.Post;
import com.petbuddy.api.model.user.User;
import com.petbuddy.api.repository.post.CommentRepository;
import com.petbuddy.api.repository.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.emptyList;

@Service
public class CommentService {

  private final PostRepository postRepository;

  private final CommentRepository commentRepository;

  public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
  }

  @Transactional
  public Comment write(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId, Comment comment) {
    checkArgument(comment.getPostId().equals(postId), "comment.postId must equals postId");
    checkArgument(comment.getUserId().equals(userId), "comment.userId must equals userId");
    checkNotNull(comment, "comment must be provided.");

    return findPost(postId, postWriterId, userId)
      .map(post -> {
        post.incrementAndGetComments();
        postRepository.update(post);
        return insert(comment);
      })
      .orElseThrow(() -> new NotFoundException(Post.class, postId, userId));
  }

  @Transactional(readOnly = true)
  public List<Comment> findAll(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId) {
    return findPost(postId, postWriterId, userId)
      .map(post -> commentRepository.findAll(postId))
      .orElse(emptyList());
  }

  private Optional<Post> findPost(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId) {
    checkNotNull(postId, "postId must be provided.");
    checkNotNull(postWriterId, "postWriterId must be provided.");
    checkNotNull(userId, "userId must be provided.");

    return postRepository.findById(postId, postWriterId, userId);
  }

  private Comment insert(Comment comment) {
    return commentRepository.insert(comment);
  }

  private void update(Comment comment) {
    commentRepository.update(comment);
  }

}
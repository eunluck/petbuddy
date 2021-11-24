package com.petbuddy.api.service.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.post.Post;
import com.petbuddy.api.model.user.User;
import com.petbuddy.api.repository.post.PostLikeRepository;
import com.petbuddy.api.repository.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PostService {

  private final PostRepository postRepository;

  private final PostLikeRepository postLikeRepository;

  public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository) {
    this.postRepository = postRepository;
    this.postLikeRepository = postLikeRepository;
  }

  @Transactional
  public Post write(Post post) {
    return insert(post);
  }

  @Transactional
  public Post modify(Post post) {
    update(post);
    return post;
  }

  @Transactional
  public Optional<Post> like(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId) {
    return findById(postId, writerId, userId).map(post -> {
      if (!post.isLikesOfMe()) {
        post.incrementAndGetLikes();
        postLikeRepository.like(userId, postId);
        update(post);
      }
      return post;
    });
  }

  @Transactional(readOnly = true)
  public Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId) {
    checkNotNull(writerId, "writerId must be provided.");
    checkNotNull(postId, "postId must be provided.");
    checkNotNull(userId, "userId must be provided.");

    return postRepository.findById(postId, writerId, userId);
  }

  @Transactional(readOnly = true)
  public List<Post> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset, int limit) {
    checkNotNull(writerId, "writerId must be provided.");
    checkNotNull(userId, "userId must be provided.");
    if (offset < 0)
      offset = 0;
    if (limit < 1 || limit > 5)
      limit = 5;

    return postRepository.findAll(writerId, userId, offset, limit);
  }

  private Post insert(Post post) {
    return postRepository.insert(post);
  }

  private void update(Post post) {
    postRepository.update(post);
  }

}
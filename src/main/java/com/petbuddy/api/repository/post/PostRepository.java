package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.post.Post;
import com.petbuddy.api.model.user.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

  Post insert(Post post);

  void update(Post post);

  Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId);

  List<Post> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset, int limit);

}
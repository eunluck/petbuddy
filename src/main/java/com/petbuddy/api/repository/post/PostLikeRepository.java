package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.post.Post;
import com.petbuddy.api.model.user.User;

public interface PostLikeRepository {

  void like(Id<User, Long> userId, Id<Post, Long> postId);

}
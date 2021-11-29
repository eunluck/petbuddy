package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.User;

public interface PostLikeRepository {

  void like(Id<User, Long> userId, Id<Pet, Long> postId);

}
package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.pet.Likes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetLikeRepository extends CrudRepository<Likes,Long> {

  //void like(Id<UserInfo, Long> userId, Id<Pet, Long> postId);

}
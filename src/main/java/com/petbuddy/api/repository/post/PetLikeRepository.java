package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Likes;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetLikeRepository extends CrudRepository<Likes,Long> {

  //void like(Id<UserInfo, Long> userId, Id<Pet, Long> postId);

}
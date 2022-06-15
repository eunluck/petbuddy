package com.petbuddy.api.repository.pet;

import com.petbuddy.api.model.pet.Likes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetLikeRepository extends CrudRepository<Likes,Long> {

  //void like(Id<UserInfo, Long> userId, Id<Pet, Long> postId);

    //List<Likes> findByLikedPetId;

    @Query(value = "select A.ID, A.LIKED_PET_ID, A.TARGET_PET_ID " +
            "from LIKES A join LIKES B on  A.target_pet_id  = B.liked_pet_id  AND B.target_pet_id = A.liked_pet_id where A.LIKED_PET_ID= :id",nativeQuery = true)
    List<Likes> findLikesByLikesOfMe(@Param("id") Long id);


}
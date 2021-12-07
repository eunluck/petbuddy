package com.petbuddy.api.service.post;

import com.petbuddy.api.model.pet.Likes;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.post.PetLikeRepository;
import com.petbuddy.api.repository.post.PetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PostService {

  private final PetRepository petRepository;

  private final PetLikeRepository petLikeRepository;

  public PostService(PetRepository petRepository, PetLikeRepository petLikeRepository) {
    this.petRepository = petRepository;
    this.petLikeRepository = petLikeRepository;
  }

  @Transactional
  public Pet write(Pet pet) {
    return insert(pet);
  }

  @Transactional
  public Pet modify(Pet pet) {
    update(pet);
    return pet;
  }

  @Transactional
  public Optional<Pet> like(Long petId, Long writerId, Long userId) {
    return findById(petId, writerId, userId).map(post -> {
      if (!post.isLikesOfMe()) {
        post.incrementAndGetLikes();

        petLikeRepository.save(new Likes( userId, petId));
        update(post);
      }
      return post;
    });
  }

  @Transactional(readOnly = true)
  public Optional<Pet> findById( Long petId,Long writerId,  Long userId) {
    checkNotNull(writerId, "writerId must be provided.");
    checkNotNull(petId, "postId must be provided.");
    checkNotNull(userId, "userId must be provided.");

    return petRepository.findById(petId);
  }

  @Transactional(readOnly = true)
  public List<Pet> findAll( Long userId) {
    checkNotNull(userId, "userId must be provided.");
    /*
    if (offset < 0)
      offset = 0;
    if (limit < 1 || limit > 5)
      limit = 5;
*/

    return petRepository.findByUserId(userId);
  }

  private Pet insert(Pet pet) {
    return petRepository.save(pet);
  }

  private void update(Pet pet) {
    petRepository.save(pet);
  }

}
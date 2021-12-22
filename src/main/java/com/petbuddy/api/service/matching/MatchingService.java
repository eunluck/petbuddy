package com.petbuddy.api.service.matching;

import com.petbuddy.api.model.pet.Likes;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.repository.pet.PetLikeRepository;
import com.petbuddy.api.repository.pet.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class MatchingService {

  private final PetRepository petRepository;
  private final PetLikeRepository petLikeRepository;

  public MatchingService(PetRepository petRepository, PetLikeRepository petLikeRepository) {
    this.petRepository = petRepository;
    this.petLikeRepository = petLikeRepository;
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

    return petRepository.findByUserSeq(userId);
  }

}
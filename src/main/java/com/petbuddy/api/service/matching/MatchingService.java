package com.petbuddy.api.service.matching;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.repository.pet.PetLikeRepository;
import com.petbuddy.api.repository.pet.PetRepository;
import com.petbuddy.api.repository.user.UserSearchFilterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class MatchingService {

  private final PetRepository petRepository;
  private final PetLikeRepository petLikeRepository;
  private final UserSearchFilterRepository userSearchFilterRepository;

  public MatchingService(PetRepository petRepository, PetLikeRepository petLikeRepository, UserSearchFilterRepository userSearchFilterRepository) {
    this.petRepository = petRepository;
    this.petLikeRepository = petLikeRepository;
    this.userSearchFilterRepository = userSearchFilterRepository;
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

  @Transactional(readOnly = true)
  public List<Pet> findMatchingPets( Long userId) {
    UserSearchFilter filter = userSearchFilterRepository.findById(userId).orElseThrow(() -> new NotFoundException(Long.class,userId));


    return petRepository.findFilteringMatchingPets(
            filter.getGender(),
            filter.getNeuteringYn(),
            filter.getMinBirth(),
            filter.getMaxBirth(),
            filter.getPetGender(),
            filter.getPetBreed(),
            filter.getPetSize());
  }



}
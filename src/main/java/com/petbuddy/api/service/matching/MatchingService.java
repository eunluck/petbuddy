package com.petbuddy.api.service.matching;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.pet.PetLikeRepository;
import com.petbuddy.api.repository.pet.PetRepository;
import com.petbuddy.api.repository.user.UserRepository;
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
  private final UserRepository userRepository;

  public MatchingService(PetRepository petRepository, PetLikeRepository petLikeRepository, UserSearchFilterRepository userSearchFilterRepository, UserRepository userRepository) {
    this.petRepository = petRepository;
    this.petLikeRepository = petLikeRepository;
    this.userSearchFilterRepository = userSearchFilterRepository;
    this.userRepository = userRepository;
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
  public List<PetDto> findMatchingPets(Long userId) {

    UserInfo userInfo = userRepository.findBySeq(userId).orElseThrow(() -> new NotFoundException(Long.class,userId));


    return petRepository.findFilteringMatchingPets(userInfo.getSearchFilter(),userInfo.getRepresentativePetSeq());
  }



}
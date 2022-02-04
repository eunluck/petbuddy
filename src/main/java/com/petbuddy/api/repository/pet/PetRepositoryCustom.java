package com.petbuddy.api.repository.pet;

import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.model.pet.Pet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepositoryCustom {

  List<PetDto> findFilteringMatchingPets(UserSearchFilter userSearchFilter,Long representativePetSeq);
  Optional<PetDto> findBySeq(Long petId,Long likedPetId);



}
package com.petbuddy.api.service.pet;

import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.error.NotFoundException;
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
public class PetService {

  private final PetRepository petRepository;

  private final PetLikeRepository petLikeRepository;

  public PetService(PetRepository petRepository, PetLikeRepository petLikeRepository) {
    this.petRepository = petRepository;
    this.petLikeRepository = petLikeRepository;
  }

  @Transactional
  public Pet register(Pet pet) {
    return insert(pet);
  }

  @Transactional
  public Pet modify(Pet pet) {
    update(pet);
    return pet;
  }

  @Transactional
  public Optional<PetDto> like(Long targetPetId, Long likedPetId) {

    return findById(targetPetId,likedPetId).map(pet -> {
      if (!pet.isLikesOfMe()){
        Pet findPet = petRepository.findById(pet.getSeq()).orElseThrow(() -> new NotFoundException(Long.class,pet.getSeq()));
        findPet.incrementAndGetLikes();
        petLikeRepository.save(new Likes(likedPetId,targetPetId));
        update(findPet);
      }
        return pet;
    });
  }

  @Transactional(readOnly = true)
  public Optional<PetDto> findById(Long petId, Long likedPetId) {

    return petRepository.findBySeq(petId,likedPetId);
  }


  @Transactional(readOnly = true)
  public Optional<Pet> findById(Long petId) {

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

    return petRepository.findByUserSeq(userId);
  }

  private Pet insert(Pet pet) {
    return petRepository.save(pet);
  }

  private void update(Pet pet) {
    petRepository.save(pet);
  }

}
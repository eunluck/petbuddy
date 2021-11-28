package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.User;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

  Pet insert(Pet pet);

  void update(Pet pet);

  Optional<Pet> findById(Id<Pet, Long> postId, Id<User, Long> writerId, Id<User, Long> userId);

  List<Pet> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset, int limit);

}
package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {


  Optional<Pet> findById( Long petId);
  Optional<Pet> findBySeq( Long petId);

  List<Pet> findByUserSeq(Long userId);

}
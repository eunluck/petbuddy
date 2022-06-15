package com.petbuddy.api.repository.pet;

import com.petbuddy.api.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long>,PetRepositoryCustom {

    Optional<Pet> findById( Long petId);

    List<Pet> findByUserId(Long userId);

}
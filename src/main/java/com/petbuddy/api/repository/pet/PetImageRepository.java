package com.petbuddy.api.repository.pet;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage,Long> {


}
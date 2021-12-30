package com.petbuddy.api.repository.pet;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Gender;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PetRepositoryCustom {

  List<Pet> findFilteringMatchingPets(Gender gender, Boolean neuteringYn, LocalDate minAge, LocalDate maxAge, Gender petGender, String petBreed, String petSize);



}
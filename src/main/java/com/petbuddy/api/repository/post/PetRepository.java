package com.petbuddy.api.repository.post;

import com.petbuddy.api.configure.support.Pageable;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {


  Optional<Pet> findById( Long petId);

  List<Pet> findByUserId(Long userId, PageRequest pageable);

}
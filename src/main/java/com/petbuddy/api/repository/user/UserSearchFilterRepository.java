package com.petbuddy.api.repository.user;

import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSearchFilterRepository extends JpaRepository<UserSearchFilter,Long> {

}
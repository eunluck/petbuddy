package com.petbuddy.api.model.listener;


import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.user.UserRepository;
import com.petbuddy.api.util.BeanUtils;

import javax.persistence.PostPersist;

public class RegisterPetListener {
    @PostPersist
    public void postPersistPet(Object o) {
        UserRepository userRepository = BeanUtils.getBean(UserRepository.class);

        Pet pet = (Pet) o;

        UserInfo userInfo = pet.getUser();

        if (userInfo.getRepresentativePetId() == null){

            userInfo.updateRepresentativePetId(pet.getId());

        }
        userRepository.save(userInfo);
    }
}

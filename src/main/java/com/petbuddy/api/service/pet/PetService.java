package com.petbuddy.api.service.pet;

import com.beust.jcommander.internal.Lists;
import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.controller.pet.RegisterPetRequest;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.error.NotOwnerException;
import com.petbuddy.api.model.commons.AttachedFile;
import com.petbuddy.api.model.pet.Likes;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.PetImage;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.pet.PetImageRepository;
import com.petbuddy.api.repository.pet.PetLikeRepository;
import com.petbuddy.api.repository.pet.PetRepository;
import com.petbuddy.api.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetLikeRepository petLikeRepository;
    private final PetImageRepository petImageRepository;
    private final ImageUploader imageUploader;

    private List<PetImage> findAllPetImagesByIds(List<Long> petIds) {
        if (Objects.isNull(petIds)) {
            return Collections.emptyList();
        }
        return petImageRepository.findAllById(petIds);
    }


    @Transactional
    public List<PetImage> uploadPetImages(UserInfo user, List<AttachedFile> files) {

        return petImageRepository.saveAll(imageUploader.uploadPetImages(user.getId(), files));
    }


    @Transactional
    public Pet register(Pet request) {


        return insert(request);
    }

    @Transactional
    public Pet register(RegisterPetRequest request, UserInfo userInfo) {

        Pet pet = request.newPet(userInfo);

        pet.addImages(findAllPetImagesByIds(request.getImageIds()));

        return insert(pet);
    }

    @Transactional
    public Pet modify(Pet pet) {
        update(pet);
        return pet;
    }

    @Transactional
    public Optional<PetDto> like(Long targetPetId, Long likedPetId) {

        return findById(targetPetId, likedPetId).map(pet -> {
            Pet findPet = petRepository.findById(pet.getId()).orElseThrow(() -> new NotFoundException(Long.class, pet.getId()));
            if (!pet.isLikesOfMe()) {
                findPet.incrementAndGetLikes();
                petLikeRepository.save(new Likes(likedPetId, targetPetId));
            }else {
                findPet.decrementAndGetLikes();
                petLikeRepository.delete(new Likes(likedPetId, targetPetId));

            }
            update(findPet);
            return pet;
        });
    }



    @Transactional
    public List<Pet> friendPets(Long likedPetId){

        return petRepository.findAllById(
                findLikesByLikesOfMe(likedPetId)
                        .stream()
                        .map(Likes::getTargetPetId)
                        .collect(Collectors.toList()));
    }


    public List<Likes> findLikesByLikesOfMe( Long likedPetId) {
        return petLikeRepository.findLikesByLikesOfMe( likedPetId);
    }




    @Transactional(readOnly = true)
    public Optional<PetDto> findById(Long petId, Long likedPetId) {

        return petRepository.findById(petId, likedPetId);
    }


    @Transactional(readOnly = true)
    public Optional<Pet> findById(Long petId) {

        return petRepository.findById(petId);
    }


    @Transactional(readOnly = true)
    public List<Pet> findAll(Long userId) {
        checkNotNull(userId, "userId must be provided.");
    /*
    if (offset < 0)
      offset = 0;
    if (limit < 1 || limit > 5)
      limit = 5;
*/

        return petRepository.findByUserId(userId);
    }

    private Pet insert(Pet pet) {
        return petRepository.save(pet);
    }

    private Pet update(Pet pet) {
        return petRepository.save(pet);
    }

    @Transactional
    public Pet updatePet(Long petId, RegisterPetRequest request, Long userId) {

        Pet pet = findById(petId).orElseThrow(() -> new NotFoundException(Long.class, petId));

        if (pet.getUser().getId() != userId) {
            throw new NotOwnerException("펫ID:" + pet.getId().toString());
        }

        pet.updatePet(request);
        pet.updateImages(CollectionUtils.isEmpty(request.getImageIds()) ? Lists.newArrayList() : petImageRepository.findAllById(request.getImageIds()));

        return pet;

    }

    @Transactional
    public UserInfo updateRepresentPet(Long petId, UserInfo user) {

        Pet pet = findById(petId).orElseThrow(() -> new NotFoundException(Long.class, petId));

        user.updateRepresentativePetId(petId);

        return user;

    }
}
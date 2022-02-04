package com.petbuddy.api.service.pet;

import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.service.user.UserService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private PetService petService;
  @Autowired private UserService userService;
  private Long petId;

  private Long writerId;

  private Long userId;
  private UserInfo userInfo;
  private Pet pet = null;
  private Pet likedPet = null;
  private UserInfo user2 = null;
  private Pet pet2 = null;
  @BeforeAll
  void setUp() {
    userInfo = userService.join("테스트",new Email("skgoddns1@gmail.com","user"),"0694123",Gender.of("MALE"));
    user2 =  userService.join("테스트2",new Email("skgoddns2@gmail.com","user"),"0694123",Gender.of("FEMALE"));

  }


  @Test
  @Order(1)
  void 펫을_등록한다() {
    String contents = randomAlphabetic(10);
    String male = "male";

    pet = petService.register(new Pet(userInfo,"삐삐", Gender.of(male),1,false,contents));
    pet2 = petService.register(new Pet(user2,"뽀삐", Gender.of("FEMALE"),3,true,contents));

    assertThat(pet, is(notNullValue()));
    assertThat(pet.getSeq(), is(notNullValue()));
    assertThat(pet.getPetIntroduce(), is(contents));
    log.info("Written post: {}", pet);
  }

  @Test
  @Order(2)
  void 펫을_수정한다() {
    Pet petBefore = petService.findById(pet.getSeq()).orElse(null);
    assertThat(petBefore, is(notNullValue()));
    String petIntroduce = randomAlphabetic(40);
    petBefore.modifyPetIntroduce(petIntroduce);
    pet = petService.modify(petBefore);
    assertThat(pet.getPetIntroduce(), is(petIntroduce));
    log.info("Modified post: {}", pet);
  }

  @Test
  @Order(3)
  void 나의_펫_목록을_조회한다() {
    List<Pet> pets = petService.findAll( userInfo.getSeq());
    assertThat(pets, is(notNullValue()));
    assertThat(pets.size(), is(1));
  }
/*

  @Test
  @Order(4)
  void 펫_좋아요() {
    PetDto pet;

    UserInfo currentUser = userService.findById(userInfo.getSeq()).orElseThrow(() -> new NotFoundException(Long.class,userId));


    pet = petService.findById(pet2.getSeq(),currentUser.getRepresentativePetSeq()).orElse(null);

    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(false));

    int beforeLikes = pet.getLikes();


    pet = petService.like(pet.getSeq(),  currentUser.getRepresentativePetSeq()).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(true));
    assertThat(pet.getLikes(), is(beforeLikes + 1));
  }

  @Test
  @Order(5)
  void 펫을_중복으로_좋아할수없다() {
    PetDto pet;

    UserInfo user = userService.findById(userInfo.getSeq()).orElseThrow(() -> new NotFoundException(Long.class,userId));

    pet = petService.findById(petId,user.getRepresentativePetSeq()).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(true));

    int beforeLikes = pet.getLikes();

    pet = petService.like(petId, user.getRepresentativePetSeq()).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(true));
    assertThat(pet.getLikes(), is(beforeLikes));
  }
*/

}
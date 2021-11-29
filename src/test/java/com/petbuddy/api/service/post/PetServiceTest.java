package com.petbuddy.api.service.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.Writer;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.User;
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

  @Autowired private PostService postService;

  private Id<Pet, Long> postId;

  private Id<User, Long> writerId;

  private Id<User, Long> userId;

  @BeforeAll
  void setUp() {
    postId = Id.of(Pet.class, 1L);
    writerId = Id.of(User.class, 1L);
    userId = Id.of(User.class, 2L);
  }

  @Test
  @Order(1)
  void 포스트를_작성한다() {
    String contents = randomAlphabetic(40);
    Pet pet = postService.write(new Pet(writerId, contents));
    assertThat(pet, is(notNullValue()));
    assertThat(pet.getSeq(), is(notNullValue()));
    assertThat(pet.getPetIntroduce(), is(contents));
    log.info("Written post: {}", pet);
  }

  @Test
  @Order(2)
  void 포스트를_수정한다() {
    Pet pet = postService.findById(postId, writerId, userId).orElse(null);
    assertThat(pet, is(notNullValue()));
    String petIntroduce = randomAlphabetic(40);
    pet.modifyPetIntroduce(petIntroduce);
    postService.modify(pet);
    assertThat(pet.getPetIntroduce(), is(petIntroduce));
    log.info("Modified post: {}", pet);
  }

  @Test
  @Order(3)
  void 포스트_목록을_조회한다() {
    List<Pet> pets = postService.findAll(writerId, userId, 0, 20);
    assertThat(pets, is(notNullValue()));
    assertThat(pets.size(), is(4));
  }

  @Test
  @Order(4)
  void 포스트를_처음으로_좋아한다() {
    Pet pet;

    pet = postService.findById(postId, writerId, userId).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(false));

    int beforeLikes = pet.getLikes();

    pet = postService.like(postId, writerId, userId).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(true));
    assertThat(pet.getLikes(), is(beforeLikes + 1));
  }

  @Test
  @Order(5)
  void 포스트를_중복으로_좋아할수없다() {
    Pet pet;

    pet = postService.findById(postId, writerId, userId).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(true));

    int beforeLikes = pet.getLikes();

    pet = postService.like(postId, writerId, userId).orElse(null);
    assertThat(pet, is(notNullValue()));
    assertThat(pet.isLikesOfMe(), is(true));
    assertThat(pet.getLikes(), is(beforeLikes));
  }

}
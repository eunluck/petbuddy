/*
package com.petbuddy.api.service.post;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Comment;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.PetOwner;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.UserInfo;
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
class CommentServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private PostService postService;

  @Autowired private CommentService commentService;

  private Id<Pet, Long> postId;

  private Id<UserInfo, Long> postWriterId;

  private Id<UserInfo, Long> userId;

  @BeforeAll
  void setUp() {
    postId = Id.of(Pet.class, 1L);
    postWriterId = Id.of(UserInfo.class, 1L);
    userId = Id.of(UserInfo.class, 2L);
  }

  @Test
  @Order(1)
  void 코멘트를_작성한다() {
    String contents = randomAlphabetic(40);
    Pet beforePet = postService.findById(postId, postWriterId, userId).orElseThrow(() -> new NotFoundException(Pet.class, postId));
    Comment comment = commentService.write(
      postId,
      postWriterId,
      userId,
      new Comment(userId, postId, new PetOwner(new Email("test00@gmail.com","user"), "test00"), contents)
    );
    Pet afterPet = postService.findById(postId, postWriterId, userId).orElseThrow(() -> new NotFoundException(Pet.class, postId));
    assertThat(comment, is(notNullValue()));
    assertThat(comment.getSeq(), is(notNullValue()));
    assertThat(comment.getContents(), is(contents));
    log.info("Written comment: {}", comment);
  }

  @Test
  @Order(2)
  void 댓글_목록을_조회한다() {
    List<Comment> comments = commentService.findAll(postId, postWriterId, userId);
    assertThat(comments, is(notNullValue()));
    assertThat(comments.size(), is(2));
  }

}*/

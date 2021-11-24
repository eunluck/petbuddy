package com.petbuddy.api.service.user;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.ConnectedUser;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private UserService userService;

  private String name;

  private Email email;

  private String password;

  @BeforeAll
  void setUp() {
    name = "tester";
    email = new Email("test@gmail.com");
    password = "1234";
  }

  @Test
  @Order(1)
  void 사용자를_추가한다() {
    User user = userService.join(name, email, password);
    assertThat(user, is(notNullValue()));
    assertThat(user.getSeq(), is(notNullValue()));
    assertThat(user.getEmail(), is(email));
    log.info("Inserted user: {}", user);
  }

  @Test
  @Order(2)
  void 사용자를_이메일로_조회한다() {
    User user = userService.findByEmail(email).orElse(null);
    assertThat(user, is(notNullValue()));
    assertThat(user.getEmail(), is(email));
    log.info("Found by {}: {}", email, user);
  }

  @Test
  @Order(3)
  void 이메일로_로그인한다() {
    User user = userService.login(email, password);
    assertThat(user, is(notNullValue()));
    assertThat(user.getEmail(), is(email));
    assertThat(user.getLoginCount(), is(1));
    log.info("First login: {}", user);

    user = userService.login(email, password);
    assertThat(user, is(notNullValue()));
    assertThat(user.getEmail(), is(email));
    assertThat(user.getLoginCount(), is(2));
    log.info("Second login: {}", user);
  }

  @Test
  @Order(4)
  void 잘못된_비밀번호로_로그인을_할수없다() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> userService.login(email, "invalid password"));
  }

  @Test
  @Order(5)
  void 친구_목록을_가져온다() {
    List<ConnectedUser> connected = userService.findAllConnectedUser(Id.of(User.class, 1L));
    assertThat(connected, is(notNullValue()));
    assertThat(connected.size(), is(1));
  }

  @Test
  @Order(6)
  void 친구ID_목록을_가져온다() {
    List<Id<User, Long>> connectedIds = userService.findConnectedIds(Id.of(User.class, 1L));
    assertThat(connectedIds, is(notNullValue()));
    assertThat(connectedIds.size(), is(1));
    assertThat(connectedIds.get(0).value(), is(2L));
  }

}
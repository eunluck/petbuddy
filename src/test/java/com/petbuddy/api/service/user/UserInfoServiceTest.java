package com.petbuddy.api.service.user;

import com.petbuddy.api.configure.JwtTokenConfigure;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import java.lang.annotation.Target;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserInfoServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private UserService userService;

  private String name;
  private Email email;
  private String password;
  private
  @BeforeAll
  void setUp() {
    name = "tester";
    email = new Email("test@gmail.com","user");
    password = "1234";
  }

  @Test
  @Order(1)
  void 사용자를_추가한다() {
    UserInfo userInfo = userService.join(name, email, password, Gender.of("MALE"));
    assertThat(userInfo, is(notNullValue()));
    assertThat(userInfo.getId(), is(notNullValue()));
    assertThat(userInfo.getEmail(), is(email));
    log.info("Inserted user: {}", userInfo);
  }

  @Test
  @Order(2)
  void 사용자를_이메일로_조회한다() {
    UserInfo userInfo = userService.findByEmail(email).orElse(null);
    assertThat(userInfo, is(notNullValue()));
    assertThat(userInfo.getEmail(), is(email));
    log.info("Found by {}: {}", email, userInfo);
  }

  @Test
  @Order(3)
  void 이메일로_로그인한다() {
    UserInfo userInfo = userService.login(email, password);
    assertThat(userInfo, is(notNullValue()));
    assertThat(userInfo.getEmail(), is(email));
    log.info("First login: {}", userInfo);

    userInfo = userService.login(email, password);
    assertThat(userInfo, is(notNullValue()));
    assertThat(userInfo.getEmail(), is(email));
    log.info("Second login: {}", userInfo);
  }

  @Test
  @Order(4)
  void 잘못된_비밀번호로_로그인을_할수없다() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> userService.login(email, "invalid password"));
  }


}
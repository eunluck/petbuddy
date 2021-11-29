package com.petbuddy.api.security;

import com.petbuddy.api.model.user.Email;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private Jwt jwt;

  @BeforeAll
  void setUp() {
    String issuer = "social-server";
    String clientSecret = "Rel5Bjce6MajBo08qgkNgYaTuzvJe6iwnBFhsD11";
    int expirySeconds = 10;

    jwt = new Jwt(issuer, clientSecret, expirySeconds);
  }

  @Test
  void JWT_토큰을_생성하고_복호화_할수있다() {
    Jwt.Claims claims = Jwt.Claims.of(1L,"tester", new Email("test@gmail.com","user"), new String[]{"ROLE_USER"});
    String encodedJWT = jwt.newToken(claims);
    log.info("encodedJWT: {}", encodedJWT);

    Jwt.Claims decodedJWT = jwt.verify(encodedJWT);
    log.info("decodedJWT: {}", decodedJWT);

    assertThat(claims.userKey, Is.is(decodedJWT.userKey));
    assertArrayEquals(claims.roles, decodedJWT.roles);
  }

  @Test
  void JWT_토큰을_리프레시_할수있다() throws Exception {
    if (jwt.getExpirySeconds() > 0) {
      Jwt.Claims claims = Jwt.Claims.of(1L, "tester", new Email("test@gmail.com","user"), new String[]{"ROLE_USER"});
      String encodedJWT = jwt.newToken(claims);
      log.info("encodedJWT: {}", encodedJWT);

      // 1초 대기 후 토큰 갱신
      sleep(1_000L);

      String encodedRefreshedJWT = jwt.refreshToken(encodedJWT);
      log.info("encodedRefreshedJWT: {}", encodedRefreshedJWT);

      assertThat(encodedJWT, not(encodedRefreshedJWT));

      Jwt.Claims oldJwt = jwt.verify(encodedJWT);
      Jwt.Claims newJwt = jwt.verify(encodedRefreshedJWT);

      long oldExp = oldJwt.exp();
      long newExp = newJwt.exp();

      // 1초 후에 토큰을 갱신했으므로, 새로운 토큰의 만료시각이 1초 이후임
      Assert.assertTrue(newExp >= oldExp + 1_000L);

      log.info("oldJwt: {}", oldJwt);
      log.info("newJwt: {}", newJwt);
    }
  }

}
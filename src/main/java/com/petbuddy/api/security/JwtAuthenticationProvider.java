package com.petbuddy.api.security;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.Role;
import com.petbuddy.api.model.user.User;
import com.petbuddy.api.service.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final Jwt jwt;

  private final UserService userService;

  public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
    this.jwt = jwt;
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
    return processUserAuthentication(authenticationToken.authenticationRequest());
  }

  private Authentication processUserAuthentication(AuthenticationRequest request) {
    try {
      User user = userService.login(new Email(request.getPrincipal()), request.getCredentials());
      JwtAuthenticationToken authenticated =
        new JwtAuthenticationToken(
          new JwtAuthentication(user.getSeq(), user.getName(), user.getEmail()),
          null,
          AuthorityUtils.createAuthorityList(Role.USER.value())
        );
      String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
      authenticated.setDetails(new AuthenticationResult(apiToken, user));
      return authenticated;
    } catch (NotFoundException e) {
      throw new UsernameNotFoundException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException(e.getMessage());
    } catch (DataAccessException e) {
      throw new AuthenticationServiceException(e.getMessage(), e);
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return isAssignable(JwtAuthenticationToken.class, authentication);
  }

}
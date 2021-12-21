package com.petbuddy.api.service.user;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.event.JoinEvent;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.user.UserRepository;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {
  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final EventBus eventBus;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, EventBus eventBus) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.eventBus = eventBus;
  }

  @Transactional
  public UserInfo join(String name, Email email, String password) {
    checkArgument(isNotEmpty(password), "password must be provided.");
    checkArgument(
      password.length() >= 4 && password.length() <= 15,
      "password length must be between 4 and 15 characters."
    );

    UserInfo userInfo = new UserInfo(name, email, passwordEncoder.encode(password));
    UserInfo saved = insert(userInfo);

    // raise event
    eventBus.post(new JoinEvent(saved));
    return saved;
  }

  @Transactional
  public UserInfo login(Email email, String password) {
    checkNotNull(password, "password must be provided.");

    UserInfo userInfo = findByEmail(email)
      .orElseThrow(() -> new NotFoundException(UserInfo.class, email));
    userInfo.login(passwordEncoder, password);
    userInfo.afterLoginSuccess();
    update(userInfo);
    return userInfo;
  }

  @Transactional
  public UserInfo updateProfileImage( Long userId, String profileImageUrl) {
    UserInfo userInfo = findById(userId)
      .orElseThrow(() -> new NotFoundException(UserInfo.class, userId));
    userInfo.updateProfileImage(profileImageUrl);
    update(userInfo);
    return userInfo;
  }

  @Transactional(readOnly = true)
  public Optional<UserInfo> findById( Long userId) {
    checkNotNull(userId, "userId must be provided.");

    return userRepository.findBySeq(userId);
  }

  @Transactional(readOnly = true)
  public Optional<UserInfo> findByEmail(Email email) {
    checkNotNull(email, "email must be provided.");

    return userRepository.findByEmail(email);
  }


  private UserInfo insert(UserInfo userInfo) {
    return userRepository.save(userInfo);
  }

  private void update(UserInfo userInfo) {
    userRepository.save(userInfo);
  }

}
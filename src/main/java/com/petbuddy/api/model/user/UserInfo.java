package com.petbuddy.api.model.user;

import com.beust.jcommander.internal.Lists;
import com.petbuddy.api.model.BaseEntity;
import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.listener.UserSearchFilterListener;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.security.Jwt;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(value = UserSearchFilterListener.class)
public class UserInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;
  private String name;
  @Enumerated(value = EnumType.STRING)
  private Gender gender;
  private LocalDate birth;
  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "address", column = @Column(name = "email")),
          @AttributeOverride(name = "emailType", column = @Column(name = "email_type"))
  })
  private Email email;
  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "addressJb", column = @Column(name = "address_jb")),
          @AttributeOverride(name = "addressSt", column = @Column(name = "address_st")),
          @AttributeOverride(name = "addressEtc", column = @Column(name = "address_etc"))
  })
  private Address address;
  private String password;
  private String profileImageUrl;
  private int status;
  private LocalDateTime lastLoginAt;
  @OneToOne
  private UserSearchFilter searchFilter;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_seq",insertable = false,updatable = false)
  private List<Pet> pets = Lists.newArrayList();

  public UserInfo(String name, Email email, String password, Gender gender) {
    this(name, email, password, gender,null);
  }

  public UserInfo(String name, Email email, String password, Gender gender,String profileImageUrl) {
    this(null, name, email, password, gender, profileImageUrl,  null );
  }

  public UserInfo(Long seq, String name, Email email, String password, Gender gender, String profileImageUrl, LocalDateTime lastLoginAt) {
    checkArgument(isNotEmpty(name), "name must be provided.");
    checkArgument(
      name.length() >= 1 && name.length() <= 10,
      "name length must be between 1 and 10 characters."
    );
    checkNotNull(email, "email must be provided.");
    checkNotNull(password, "password must be provided.");
    checkNotNull(gender, "성별을 입력해 주세요.");
    checkArgument(
      profileImageUrl == null || profileImageUrl.length() <= 255,
      "profileImageUrl length must be less than 255 characters."
    );

    this.seq = seq;
    this.name = name;
    this.email = email;
    this.gender = gender;
    this.password = password;
    this.profileImageUrl = profileImageUrl;
    this.lastLoginAt = lastLoginAt;
    this.status = 1;
  }

  public void login(PasswordEncoder passwordEncoder, String credentials) {
    if (!passwordEncoder.matches(credentials, password))
      throw new IllegalArgumentException("Bad credential");
  }

  public void afterLoginSuccess() {
    lastLoginAt = now();
  }

  public String newApiToken(Jwt jwt, String[] roles) {
    Jwt.Claims claims = Jwt.Claims.of(seq, name, email, roles);
    return jwt.newToken(claims);
  }

  public void updateProfileImage(String profileImageUrl) {
    checkArgument(
      profileImageUrl == null || profileImageUrl.length() <= 255,
      "profileImageUrl length must be less than 255 characters."
    );

    this.profileImageUrl = profileImageUrl;
  }


  public Optional<String> getProfileImageUrl() {
    return ofNullable(profileImageUrl);
  }

  public Optional<LocalDateTime> getLastLoginAt() {
    return ofNullable(lastLoginAt);
  }



}
package com.petbuddy.api.model.user;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Strings;
import com.petbuddy.api.controller.user.UserMoreInformationUpdateRequest;
import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.commons.BaseEntity;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.security.Jwt;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE user_info SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseEntity {

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
  private String phone;
  private String profileImageUrl;
  private int status;
  private LocalDateTime lastLoginAt;

  @OneToOne(mappedBy = "userInfo")
  @JsonManagedReference
  private UserSearchFilter searchFilter;


  @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST, orphanRemoval = true)
  @JsonManagedReference
  private List<Pet> pets = Lists.newArrayList();

  private Long representativePetId;

  public UserInfo(String name, Email email, String password, Gender gender) {
    this(name, email, password, gender,null);
  }

  public UserInfo(String name, Email email, String password, Gender gender,String profileImageUrl) {
    this(name, email, password, gender, profileImageUrl,  null );
  }

  public UserInfo( String name, Email email, String password, Gender gender, String profileImageUrl, LocalDateTime lastLoginAt) {
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
    Jwt.Claims claims = Jwt.Claims.of(getId(), name, email, roles);
    return jwt.newToken(claims);
  }

  public void updateProfileImage(String profileImageUrl) {
    checkArgument(
      profileImageUrl == null || profileImageUrl.length() <= 255,
      "profileImageUrl length must be less than 255 characters."
    );

    this.profileImageUrl = profileImageUrl;
  }

  public void updatePhoneNumber(String phoneNumber) {
    checkArgument(
            Strings.isNullOrEmpty(phoneNumber),
      "휴대폰 번호를 입력해주세요."
    );

    checkArgument(
            Pattern.matches("^[0-9]*$", phoneNumber),
            "숫자만 입력해주세요."
    );

    this.phone = phoneNumber;
  }

  public void updateRepresentativePetId(Long petId) {

    this.representativePetId = petId;

  }


  public void updateMoreInfo(UserMoreInformationUpdateRequest userMoreInformationUpdateRequest) {

    checkArgument(
            Pattern.matches("^[0-9]*$", userMoreInformationUpdateRequest.getPhone()),
            "숫자만 입력해주세요."
    );

    this.address = userMoreInformationUpdateRequest.getAddress();
    this.birth = userMoreInformationUpdateRequest.getBirth();
    this.phone = userMoreInformationUpdateRequest.getPhone();

  }


  public Optional<String> getProfileImageUrl() {
    return ofNullable(profileImageUrl);
  }

  public Optional<LocalDateTime> getLastLoginAt() {
    return ofNullable(lastLoginAt);
  }



}
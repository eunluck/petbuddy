package com.petbuddy.api.model.user;

import com.petbuddy.api.security.Jwt;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
@NoArgsConstructor
public class UserInfo {

  @Id
  @GeneratedValue
  private Long seq;

  private String name;
  private String gender;
  private String birth;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "address", column = @Column(name = "email")),
          @AttributeOverride(name = "emailType", column = @Column(name = "email_type"))
  })
  private Email email;

  private String password;

  private String profileImageUrl;

  private LocalDateTime lastLoginAt;

  @CreatedDate
  @Column(updatable = false)
  private  LocalDateTime createAt;

  public UserInfo(String name, Email email, String password) {
    this(name, email, password, null);
  }

  public UserInfo(String name, Email email, String password, String profileImageUrl) {
    this(null, name, email, password, profileImageUrl,  null, null);
  }

  public UserInfo(Long seq, String name, Email email, String password, String profileImageUrl, LocalDateTime lastLoginAt, LocalDateTime createAt) {
    checkArgument(isNotEmpty(name), "name must be provided.");
    checkArgument(
      name.length() >= 1 && name.length() <= 10,
      "name length must be between 1 and 10 characters."
    );
    checkNotNull(email, "email must be provided.");
    checkNotNull(password, "password must be provided.");
    checkArgument(
      profileImageUrl == null || profileImageUrl.length() <= 255,
      "profileImageUrl length must be less than 255 characters."
    );

    this.seq = seq;
    this.name = name;
    this.email = email;
    this.password = password;
    this.profileImageUrl = profileImageUrl;
    this.lastLoginAt = lastLoginAt;
    this.createAt = defaultIfNull(createAt, now());
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

  public Long getSeq() {
    return seq;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Optional<String> getProfileImageUrl() {
    return ofNullable(profileImageUrl);
  }

  public Optional<LocalDateTime> getLastLoginAt() {
    return ofNullable(lastLoginAt);
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(seq, userInfo.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("name", name)
      .append("email", email)
      .append("password", "[PROTECTED]")
      .append("profileImageUrl", profileImageUrl)
      .append("lastLoginAt", lastLoginAt)
      .append("createAt", createAt)
      .toString();
  }

  static public class Builder {
    private Long seq;
    private String name;
    private Email email;
    private String password;
    private String profileImageUrl;
    private int loginCount;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;

    public Builder() {}

    public Builder(UserInfo userInfo) {
      this.seq = userInfo.seq;
      this.name = userInfo.name;
      this.email = userInfo.email;
      this.password = userInfo.password;
      this.lastLoginAt = userInfo.lastLoginAt;
      this.createAt = userInfo.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder email(Email email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder profileImageUrl(String profileImageUrl) {
      this.profileImageUrl = profileImageUrl;
      return this;
    }

    public Builder loginCount(int loginCount) {
      this.loginCount = loginCount;
      return this;
    }

    public Builder lastLoginAt(LocalDateTime lastLoginAt) {
      this.lastLoginAt = lastLoginAt;
      return this;
    }

    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public UserInfo build() {
      return new UserInfo(seq, name, email, password, profileImageUrl, lastLoginAt, createAt);
    }
  }

}
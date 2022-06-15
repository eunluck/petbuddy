package com.petbuddy.api.model.user;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

@NoArgsConstructor
public class ConnectedUser {

  private  Long id;

  private  String name;

  private  Email email;

  private  String profileImageUrl;

  private  LocalDateTime grantedAt;

  public ConnectedUser(Long id, String name, Email email, String profileImageUrl, LocalDateTime grantedAt) {
    checkNotNull(id, "id must be provided.");
    checkNotNull(name, "name must be provided.");
    checkNotNull(email, "email must be provided.");
    checkNotNull(grantedAt, "grantedAt must be provided.");

    this.id = id;
    this.name = name;
    this.email = email;
    this.profileImageUrl = profileImageUrl;
    this.grantedAt = grantedAt;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Optional<String> getProfileImageUrl() {
    return ofNullable(profileImageUrl);
  }

  public LocalDateTime getGrantedAt() {
    return grantedAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("id", id)
      .append("name", name)
      .append("email", email)
      .append("profileImageUrl", profileImageUrl)
      .append("grantedAt", grantedAt)
      .toString();
  }

}
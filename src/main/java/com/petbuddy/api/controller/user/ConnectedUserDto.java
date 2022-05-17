package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.ConnectedUser;
import com.petbuddy.api.model.user.Email;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ConnectedUserDto {

  @ApiModelProperty(value = "친구 PK", required = true)
  private Long seq;

  @ApiModelProperty(value = "이름", required = true)
  private String name;

  @ApiModelProperty(value = "이메일", required = true)
  private Email email;

  @ApiModelProperty(value = "프로필 이미지 URL")
  private String profileImageUrl;

  @ApiModelProperty(value = "승락일시", required = true)
  private LocalDateTime grantedAt;

  public ConnectedUserDto(ConnectedUser source) {
    copyProperties(source, this);

    this.profileImageUrl = source.getProfileImageUrl().orElse(null);
  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public LocalDateTime getGrantedAt() {
    return grantedAt;
  }

  public void setGrantedAt(LocalDateTime grantedAt) {
    this.grantedAt = grantedAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("name", name)
      .append("email", email)
      .append("profileImageUrl", profileImageUrl)
      .append("grantedAt", grantedAt)
      .toString();
  }

}
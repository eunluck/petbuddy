package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class UserDto {

  @ApiModelProperty(value = "PK", required = true)
  private Long seq;

  @ApiModelProperty(value = "사용자명", required = true)
  private String name;

  @ApiModelProperty(value = "이메일", required = true)
  private Email email;

  @ApiModelProperty(value = "프로필 이미지 URL")
  private String profileImageUrl;

  @ApiModelProperty(value = "로그인 횟수", required = true)
  private int loginCount;

  @ApiModelProperty(value = "최종로그인일시")
  private LocalDateTime lastLoginAt;

  @ApiModelProperty(value = "생성일시", required = true)
  private LocalDateTime createAt;

  public UserDto(UserInfo source) {
    copyProperties(source, this);

    this.profileImageUrl = source.getProfileImageUrl().orElse(null);
    this.lastLoginAt = source.getLastLoginAt().orElse(null);
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

  public int getLoginCount() {
    return loginCount;
  }

  public void setLoginCount(int loginCount) {
    this.loginCount = loginCount;
  }

  public LocalDateTime getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(LocalDateTime lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("name", name)
      .append("email", email)
      .append("profileImageUrl", profileImageUrl)
      .append("loginCount", loginCount)
      .append("lastLoginAt", lastLoginAt)
      .append("createAt", createAt)
      .toString();
  }

}
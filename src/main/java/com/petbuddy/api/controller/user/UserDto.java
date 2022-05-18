package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

  @ApiModelProperty(value = "PK", required = true)
  private Long id;

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
  private LocalDateTime createdAt;

  @ApiModelProperty(value = "매칭 필터", required = true)
  private UserSearchFilter searchFilter;

  public UserDto(UserInfo source) {
    copyProperties(source, this);

    this.profileImageUrl = source.getProfileImageUrl().orElse(null);
    this.lastLoginAt = source.getLastLoginAt().orElse(null);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("id", id)
      .append("name", name)
      .append("email", email)
      .append("profileImageUrl", profileImageUrl)
      .append("loginCount", loginCount)
      .append("lastLoginAt", lastLoginAt)
      .append("createdAt", createdAt)
      .toString();
  }

}
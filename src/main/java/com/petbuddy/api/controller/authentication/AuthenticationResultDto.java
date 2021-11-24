package com.petbuddy.api.controller.authentication;

import com.petbuddy.api.controller.user.UserDto;
import com.petbuddy.api.security.AuthenticationResult;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AuthenticationResultDto {

  @ApiModelProperty(value = "API 토큰", required = true)
  private String apiToken;

  @ApiModelProperty(value = "사용자 정보", required = true)
  private UserDto user;

  public AuthenticationResultDto(AuthenticationResult source) {
    copyProperties(source, this);

    this.user = new UserDto(source.getUser());
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("apiToken", apiToken)
      .append("user", user)
      .toString();
  }

}
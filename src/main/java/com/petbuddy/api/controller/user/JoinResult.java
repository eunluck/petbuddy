package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.User;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class JoinResult {

  @ApiModelProperty(value = "API 토큰", required = true)
  private final String apiToken;

  @ApiModelProperty(value = "사용자 정보", required = true)
  private final User user;

  public JoinResult(String apiToken, User user) {
    checkNotNull(apiToken, "apiToken must be provided.");
    checkNotNull(user, "user must be provided.");

    this.apiToken = apiToken;
    this.user = user;
  }

  public String getApiToken() {
    return apiToken;
  }

  public User getUser() {
    return user;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("apiToken", apiToken)
      .append("user", user)
      .toString();
  }

}
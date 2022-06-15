package com.petbuddy.api.security;

import com.petbuddy.api.model.user.UserInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationResult {

  private final String apiToken;

  private final UserInfo userInfo;

  public AuthenticationResult(String apiToken, UserInfo userInfo) {
    checkNotNull(apiToken, "apiToken must be provided.");
    checkNotNull(userInfo, "user must be provided.");

    this.apiToken = apiToken;
    this.userInfo = userInfo;
  }

  public String getApiToken() {
    return apiToken;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("apiToken", apiToken)
      .append("user", userInfo)
      .toString();
  }

}
package com.petbuddy.api.event;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.UserInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JoinEvent {

  private final Id<UserInfo, Long> userId;

  private final String name;

  public JoinEvent(UserInfo userInfo) {
    this.userId = Id.of(UserInfo.class, userInfo.getSeq());
    this.name = userInfo.getName();
  }

  public Id<UserInfo, Long> getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("userId", userId)
        .append("name", name)
        .toString();
  }

}

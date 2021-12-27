package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class JoinRequest {

  @ApiModelProperty(value = "이름", required = true)
  private String name;

  @ApiModelProperty(value = "로그인 이메일", required = true)
  private String principal;

  @ApiModelProperty(value = "이메일 타입", required = true)
  private String emailType;

  @ApiModelProperty(value = "로그인 비밀번호", required = true)
  private String credentials;

  @ApiModelProperty(value = "가입자 성별", required = true)
  private Gender gender;



  protected JoinRequest() {}

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("name", name)
      .append("principal", principal)
      .append("credentials", credentials)
      .append("emailType", emailType)
      .toString();
  }

}
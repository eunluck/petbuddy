package com.petbuddy.api.controller.user;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JoinRequest {

  @ApiModelProperty(value = "이름", required = true)
  private String name;

  @ApiModelProperty(value = "로그인 이메일", required = true)
  private String principal;

  @ApiModelProperty(value = "이메일 타입", required = true)
  private String emailType;



  @ApiModelProperty(value = "로그인 비밀번호", required = true)
  private String credentials;

  protected JoinRequest() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public String getCredentials() {
    return credentials;
  }

  public void setCredentials(String credentials) {
    this.credentials = credentials;
  }


  public String getEmailType() {
    return emailType;
  }

  public void setEmailType(String emailType) {
    this.emailType = emailType;
  }

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
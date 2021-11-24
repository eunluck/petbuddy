package com.petbuddy.api.model.post;

import com.petbuddy.api.model.user.Email;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

public class Writer {

  @ApiModelProperty(value = "이메일", required = true)
  private final Email email;

  @ApiModelProperty(value = "이름")
  private final String name;

  public Writer(Email email) {
    this(email, null);
  }

  public Writer(Email email, String name) {
    checkNotNull(email, "email must be provided.");

    this.email = email;
    this.name = name;
  }

  public Email getEmail() {
    return email;
  }

  public Optional<String> getName() {
    return ofNullable(name);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("email", email)
      .append("name", name)
      .toString();
  }

}
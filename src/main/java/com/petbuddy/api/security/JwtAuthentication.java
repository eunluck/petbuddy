package com.petbuddy.api.security;

import com.petbuddy.api.model.user.Email;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthentication {

  public final Long id;

  public final String name;

  public final Email email;

  JwtAuthentication(Long id, String name, Email email) {
    checkNotNull(id, "id must be provided.");
    checkNotNull(name, "name must be provided.");
    checkNotNull(email, "email must be provided.");

    this.id = id;
    this.name = name;
    this.email = email;
  }

}
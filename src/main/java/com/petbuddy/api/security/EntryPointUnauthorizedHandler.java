package com.petbuddy.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petbuddy.api.controller.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.petbuddy.api.controller.ApiResult.ERROR;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

  static ApiResult<?> E401 = ApiResult.ERROR("Authentication error (cause: unauthorized)", HttpStatus.UNAUTHORIZED);

  private final ObjectMapper om;

  public EntryPointUnauthorizedHandler(ObjectMapper om) {
    this.om = om;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setHeader("content-type", "application/json");
    response.getWriter().write(om.writeValueAsString(E401));
    response.getWriter().flush();
    response.getWriter().close();
  }

}
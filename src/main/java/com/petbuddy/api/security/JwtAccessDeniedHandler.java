package com.petbuddy.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petbuddy.api.controller.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.petbuddy.api.controller.ApiResult.ERROR;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  static ApiResult<?> E403 = ApiResult.ERROR("Authentication error (cause: forbidden)", HttpStatus.FORBIDDEN);

  private final ObjectMapper om;

  public JwtAccessDeniedHandler(ObjectMapper om) {
    this.om = om;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
    throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setHeader("content-type", "application/json");
    response.getWriter().write(om.writeValueAsString(E403));
    response.getWriter().flush();
    response.getWriter().close();
  }

}
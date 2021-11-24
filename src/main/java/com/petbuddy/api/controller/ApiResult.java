package com.petbuddy.api.controller;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiResult<T> {

  @ApiModelProperty(value = "API 요청 처리 결과", required = true)
  private final boolean success;

  @ApiModelProperty(value = "success가 true라면, API 요청 처리 응답값")
  private final T response;

  @ApiModelProperty(value = "success가 false라면, API 요청 처리 응답값")
  private final ApiError error;

  private ApiResult(boolean success, T response, ApiError error) {
    this.success = success;
    this.response = response;
    this.error = error;
  }

  public static <T> ApiResult<T> OK(T response) {
    return new ApiResult<>(true, response, null);
  }

  public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
    return new ApiResult<>(false, null, new ApiError(throwable, status));
  }

  public static ApiResult<?> ERROR(String errorMessage, HttpStatus status) {
    return new ApiResult<>(false, null, new ApiError(errorMessage, status));
  }

  public boolean isSuccess() {
    return success;
  }

  public ApiError getError() {
    return error;
  }

  public T getResponse() {
    return response;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("success", success)
      .append("response", response)
      .append("error", error)
      .toString();
  }

}
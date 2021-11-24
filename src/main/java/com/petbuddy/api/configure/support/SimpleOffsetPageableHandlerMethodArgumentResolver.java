package com.petbuddy.api.configure.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static java.lang.Math.min;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.springframework.util.StringUtils.hasText;

public class SimpleOffsetPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String DEFAULT_OFFSET_PARAMETER = "offset";

  private static final String DEFAULT_LIMIT_PARAMETER = "limit";

  private static final int DEFAULT_MAX_LIMIT_SIZE = 5;

  private SimpleOffsetPageRequest fallbackPageable;

  private String offsetParameterName = DEFAULT_OFFSET_PARAMETER;

  private String limitParameterName = DEFAULT_LIMIT_PARAMETER;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Pageable.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
    MethodParameter methodParameter,
    ModelAndViewContainer mavContainer,
    NativeWebRequest webRequest,
    WebDataBinderFactory binderFactory
  ) {
    String offsetString = webRequest.getParameter(offsetParameterName);
    String limitString = webRequest.getParameter(limitParameterName);

    boolean pageAndLimitGiven = hasText(offsetString) && hasText(limitString);

    if (!pageAndLimitGiven && fallbackPageable == null)
      return null;

    long offset = hasText(offsetString) ? parseAndApplyBoundaries(offsetString, Integer.MAX_VALUE) : fallbackPageable.offset();
    int limit = hasText(limitString) ? parseAndApplyBoundaries(limitString, DEFAULT_MAX_LIMIT_SIZE) : fallbackPageable.limit();

    limit = limit < 1 ? fallbackPageable.limit() : limit;
    limit = min(limit, DEFAULT_MAX_LIMIT_SIZE);

    return new SimpleOffsetPageRequest(offset, limit);
  }

  private int parseAndApplyBoundaries(String parameter, int upper) {
    int parsed = toInt(parameter, 0);
    return parsed < 0 ? 0 : min(parsed, upper);
  }

  public void setFallbackPageable(SimpleOffsetPageRequest fallbackPageable) {
    this.fallbackPageable = fallbackPageable;
  }

  public void setOffsetParameterName(String offsetParameterName) {
    this.offsetParameterName = offsetParameterName;
  }

  public void setLimitParameterName(String limitParameterName) {
    this.limitParameterName = limitParameterName;
  }

}
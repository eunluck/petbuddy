package com.petbuddy.api.configure;

import com.petbuddy.api.configure.support.SimpleOffsetPageRequest;
import com.petbuddy.api.configure.support.SimpleOffsetPageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

  @Bean
  public SimpleOffsetPageableHandlerMethodArgumentResolver simpleOffsetPageableHandlerMethodArgumentResolver() {
    SimpleOffsetPageableHandlerMethodArgumentResolver resolver = new SimpleOffsetPageableHandlerMethodArgumentResolver();
    resolver.setFallbackPageable(new SimpleOffsetPageRequest(0, 5));
    return resolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(simpleOffsetPageableHandlerMethodArgumentResolver());
  }

}
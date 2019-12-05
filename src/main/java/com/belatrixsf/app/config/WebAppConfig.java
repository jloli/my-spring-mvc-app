package com.belatrixsf.app.config;

import com.belatrixsf.app.web.controller.LoggingInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.belatrixsf.app.web")
public class WebAppConfig implements WebMvcConfigurer {

  private final LoggingInterceptor loggingInterceptor;


  public WebAppConfig(LoggingInterceptor loggingInterceptor) {
    this.loggingInterceptor = loggingInterceptor;
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggingInterceptor);
  }


}

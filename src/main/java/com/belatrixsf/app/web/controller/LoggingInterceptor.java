package com.belatrixsf.app.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);


  private final RequestId requestId;


  public LoggingInterceptor(RequestId requestId) {
    this.requestId = requestId;
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    log.debug("PreHandle for {} with request id {}", handler, requestId.getId());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

    log.debug("PostHandle for {} with request id {}", handler, requestId.getId());
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

    log.debug("AfterCompletion for {} with request id {}", handler, requestId.getId());
  }

}

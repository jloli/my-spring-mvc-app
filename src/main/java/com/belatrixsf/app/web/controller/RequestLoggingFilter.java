package com.belatrixsf.app.web.controller;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLoggingFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.debug("Before call DispatcherServlet....");

    chain.doFilter(request, response);

    log.debug("After DispatcherServlet was called...");
  }

  @Override
  public void destroy() {

  }
}

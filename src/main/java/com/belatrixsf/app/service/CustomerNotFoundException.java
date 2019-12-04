package com.belatrixsf.app.service;

import org.springframework.core.NestedRuntimeException;

public class CustomerNotFoundException extends NestedRuntimeException {

  private static final String DEFAULT_MESSAGE = "Customer with id '%s' not found.";

  private final long id;

  public CustomerNotFoundException(long id) {
    super(String.format(DEFAULT_MESSAGE, id));
    this.id = id;
  }

  public long getCustomerId() {
    return id;
  }

}

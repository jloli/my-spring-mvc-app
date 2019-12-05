package com.belatrixsf.app.web.controller;

import java.util.UUID;

public class RequestId {

  private final String id;

  public RequestId() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

}

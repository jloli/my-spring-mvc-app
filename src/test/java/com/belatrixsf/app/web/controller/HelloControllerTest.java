package com.belatrixsf.app.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HelloControllerTest {

  private HelloController helloController;

  @Before
  public void setup() {
    helloController = new HelloController();
  }


  @Test
  public void testSayHello() {
    assertThat(helloController.sayHello()).isEqualTo("Hello");
  }

  @Test
  public void testSayHelloJson() {
    HelloMessage message = helloController.sayHelloJson();
    assertThat(message).isNotNull();
    assertThat(message.getMessage()).isEqualTo("Hello");
  }

}

package com.belatrixsf.app.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
//http://localhost:8080/hello
@RequestMapping("/hello")
public class HelloController {

  @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
  public String sayHello() {
    return "Hello";
  }

  @GetMapping(produces = "application/json")
  public HelloMessage sayHelloJson() {
    return new HelloMessage("Hello");
  }

}

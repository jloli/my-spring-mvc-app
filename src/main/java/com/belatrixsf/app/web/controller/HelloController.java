package com.belatrixsf.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/hello")
public class HelloController {

  @GetMapping(produces = "text/plain")
  public String sayHello() {
    return "Hello";
  }

  @GetMapping(produces = "application/json")
  public HelloMessage sayHelloJson() {
    return new HelloMessage("Hello");
  }

}

package myapplication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

  private final String email = "nguyenminhkhoi.red@gmail.com";

  @RequestMapping(value = "/email")
  public String sayEmail() {
    return "Email:" + email;
  }
}

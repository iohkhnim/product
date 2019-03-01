package myapplication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

  private String email = "nguyenminhkhoi.red@gmail.com";

  @RequestMapping(value = "/email")
  public String Email() {
    return "Email:" + email;
  }
}

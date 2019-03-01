package myapplication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {

  @RequestMapping(value = "/help")
  public String sendHelp() {
    return "Help";
  }
}

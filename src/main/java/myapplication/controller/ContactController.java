package myapplication.controller;

import myapplication.dto.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @PropertySource("classpath:application.properties")
public class ContactController {

  private @Value("${phone}")
  String phone;
  private @Value("${name}")
  String name;

  @RequestMapping(value = "/contact")
  public Contact ContactIndex() {
    return new Contact(name, phone);
  }
}

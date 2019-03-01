package controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  private String Email = "nguyenminhkhoi.red@gmail.com";

  @RequestMapping(value = "/hello/{name}")
  public String Hello(@PathVariable("name") String myname) {
    return "Hello " + myname;
  }
}

package myapplication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {

  @RequestMapping(value = "/calculate")
  public String calculateInteger(@RequestParam("p1") String p1, @RequestParam("p2") String p2) {
    try {
      int result = Integer.valueOf(p1) + Integer.valueOf(p2);
      return Integer.valueOf(result).toString();
    } catch (Exception ex) {
      return "Error";
    }
  }
}

package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {
    @RequestMapping(value = "/calculate")
    public String Calculate(@RequestParam("p1") String p1, @RequestParam("p2") String p2){
        try
        {
            int p11 = Integer.valueOf(p1);
            int p22 = Integer.valueOf(p2);
            int p33 = p11 + p22;
            return Integer.valueOf(p33).toString();
        }
        catch (Exception ex)
        {
            return "Error";
        }
    }
}

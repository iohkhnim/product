package com.example.demo;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Contact;

@RestController
public class helloworldController {

    private String Email = "nguyenminhkhoi.red@gmail.com";
    private String Name = "Nguyen Minh Khoi";
    private String Phone = "0707333124";
    @RequestMapping(value = "/")
    public String helloworld() {
        return "Hello World.";
    }
    @RequestMapping(value = "/help")
    public String Help()    {
        return "Help";
    }
    @RequestMapping(value = "/email")
    public String Email(){
        return "Email:" + Email;
    }
    @RequestMapping(value = "/contact")
    public Contact contact(){
        Contact contact = new Contact(Name, Phone);
        return contact;
    }
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

    @RequestMapping(value = "/hello/{name}")
    public String Hello(@PathVariable("name") String myname)
    {
        return "Hello " + myname;
    }
}

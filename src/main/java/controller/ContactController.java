package controller;

import dto.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@PropertySource("classpath:application.properties")
public class ContactController {
    private @Value("${phone}") String Phone;
    private @Value("${name}") String Name;
    @RequestMapping(value = "/contact")
    public Contact contact(){
        return new Contact(Name, Phone);
    }

}

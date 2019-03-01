package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import controller.Contact;
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

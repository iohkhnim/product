package myapplication;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import myapplication.service.service.impl.ProductServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  @PostConstruct
  public void init() {
    // Setting Spring Boot SetTimeZone
    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}

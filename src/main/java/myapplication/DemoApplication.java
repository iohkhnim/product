package myapplication;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    //if using Google Cloud authentication
    /*
    try {
      GoogleCredential cred = GoogleCredential.fromStream(new FileInputStream(
          "D:\\product\\src\\main\\java\\myapplication\\key\\service_account.json"));
      RSAPrivateKey privateKey = (RSAPrivateKey) cred.getServiceAccountPrivateKey();
      String privateKeyId = cred.getServiceAccountPrivateKeyId();

      long now = System.currentTimeMillis();

      Algorithm algorithm = Algorithm.RSA256(null, privateKey);

      String signedJwt = JWT.create()
          .withKeyId(privateKeyId)
          .withIssuer("")
          .withAudience("")
          .withIssuedAt(new Date(now))
          .withExpiresAt(new Date(now + 3600 * 1000L))
          .sign(algorithm);
    } catch (Exception ex) {
      ex.printStackTrace();
    }*/
  }

  @PostConstruct
  public void init() {
    //HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
    // Setting Spring Boot SetTimeZone
    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
  }

}


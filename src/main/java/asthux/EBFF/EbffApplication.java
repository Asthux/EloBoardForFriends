package asthux.EBFF;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EbffApplication {

  public static void main(String[] args) {
    SpringApplication.run(EbffApplication.class, args);
  }

}

package net.cloudy.sytes.hello_liberty;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

 @Configuration
public class DBInit {
     private static final Logger log = LoggerFactory.getLogger(DBInit.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepo repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
      log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
  }
}

package net.cloudy.sytes.hello_liberty.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import net.cloudy.sytes.hello_liberty.jpa.model.Employee;

@Slf4j
@Configuration
public class DBInit {

  @Bean
  CommandLineRunner initDatabase(EmployeeRepo repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
      log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
  }
}

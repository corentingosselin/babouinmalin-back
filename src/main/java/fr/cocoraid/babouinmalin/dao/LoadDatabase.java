package fr.cocoraid.babouinmalin.dao;

import fr.cocoraid.babouinmalin.model.User;
import fr.cocoraid.babouinmalin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UserService service) {

    return args -> {
      /*log.info("Preloading " + service.createUser(new User("Gosselin","Corentin","coco@gmail.com","test")));
      log.info("Preloading " + service.createUser(new User("Duboscq", "Francis","francis@gmail.com","test")));
      String crypted = service.getUser(UUID.fromString("4724adc0-a403-4a6d-8a54-2674d06dcf9e")).getPassword();
      log.info("password " + crypted);
      log.info("password test");
      log.info("match ? " + service.isSamePassword("test",crypted));*/
    };
  }
}
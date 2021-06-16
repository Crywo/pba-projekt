package io.swagger.configuration;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.switch_api.model.Task;
import io.swagger.switch_api.model.Task.StatusEnum;
import io.swagger.switch_api.model.User.GenderEnum;
import io.swagger.switch_api.model.UserRepository;
import io.swagger.switch_api.model.TaskRepository;
import io.swagger.switch_api.model.User;


@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(TaskRepository taskRepo, UserRepository userRepo) {

  	Task task1 = new Task("sample task", UUID.fromString("123e4567-e89b-12d3-a456-426614174022"), "task 1", 1);
	task1.setStatus(StatusEnum.CREATED);
	Task task2 = new Task("sample task2", UUID.fromString("223e4567-e89b-12d3-a456-426614174022"), "task 2", 0);
	task2.setStatus(StatusEnum.FINISHED);
	User user1 = new User(25,"test@gmail.com", UUID.fromString("123e4567-e89b-12d3-a456-426614174022"), "Bartosz", "Jarosiewicz", "pass123", "user123", "ROLE_USER");
	user1.setGender(GenderEnum.M);
	User user2 = new User(20,"admin@zut.edu.pl", UUID.fromString("223e4567-e89b-12d3-a456-426614174022"), "admin", "admin", "admin", "admin", "ROLE_ADMIN");
	user2.setGender(GenderEnum.F);
    return args -> {
      log.info("Preloading " + taskRepo.save(task1));
      log.info("Preloading " + taskRepo.save(task2));
      log.info("Preloading " + userRepo.save(user1));
      log.info("Preloading " + userRepo.save(user2));
    };
  }
}
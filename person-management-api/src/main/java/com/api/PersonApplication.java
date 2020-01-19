package com.api;

import com.api.security.entity.User;
import com.api.security.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class PersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class, args);
    }

    @Bean
    @Profile(value = {"development", "production"})
    ApplicationRunner init(UserRepository userRepository) {
        return (ApplicationArguments args) -> dataSetup(userRepository);
    }

    private void dataSetup(UserRepository userRepository) {
        User niraj = new User("niraj.sonawane@gmail.com", "$2a$10$yRxRYK/s8vZCp.bgmZsD/uXmHjekuPU/duM0iPZw04ddt1ID9H7kK", "Admin");
        User test = new User("test@gmail.com", "$2a$10$YWDqYU0XJwwBogVycbfPFOnzU7vsG/XvAyQlrN34G/oA1SbhRW.W.", "User");
        userRepository.save(niraj);
        userRepository.save(test);
    }

}

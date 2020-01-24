package com.api;

import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.model.Colour;
import com.api.person.repository.PersonRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableWebFlux
@CrossOrigin(origins = "http://localhost:4200")
public class PersonManagementApiReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonManagementApiReactiveApplication.class, args);
	}



}

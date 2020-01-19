package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class PersonManagementApiReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonManagementApiReactiveApplication.class, args);
	}

}

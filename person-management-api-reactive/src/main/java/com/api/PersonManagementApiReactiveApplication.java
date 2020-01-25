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

	@Bean
	@ConditionalOnProperty(name = "enabled.usersetup.onstartup", havingValue = "true")
	ApplicationRunner init(PersonRepository  repository) {
		return (ApplicationArguments args) ->  dataSetup(repository);
	}

	private void dataSetup(PersonRepository repository) {

		IntStream
				.range(1, 10)
				.mapToObj(it -> getData(it))
				.map(it->repository.save(it).block())
				.count();
	}

	Person getData(int number){
		return Person
				.builder()
				.age(number)
				.favouriteColour(Colour.BLACK)
				.firstName("User " + number)
				.lastName("Test")
				.hobby(Arrays.asList(Hobby.builder().hobbyName("Test").build()))
				.build();
	}



}

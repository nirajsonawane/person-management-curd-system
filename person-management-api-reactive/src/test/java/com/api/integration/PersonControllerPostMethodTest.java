package com.api.integration;

import com.api.person.enity.Person;
import com.api.person.model.CreatePersonRequest;
import com.api.person.model.UpdatePersonRequest;
import com.api.person.repository.PersonRepository;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
@ExtendWith(RandomBeansExtension.class)
@ActiveProfiles(value = "integration")
class PersonControllerPostMethodTest {

    @Random
    private CreatePersonRequest person;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonRepository personRepository;


    @BeforeEach
    public  void setup(){
        personRepository.deleteAll().block();
    }
    @Test
    @DisplayName("Should Create Person For Valid Request")
    void shouldReturnIsCreatedAndSavePersonInDatabase() throws Exception {
        webTestClient.post()
                     .uri(URI.create("/person/"))
                     .body(Mono.just(person), CreatePersonRequest.class)
                     .exchange()
                     .expectStatus().isCreated();
        Flux<Person> all = personRepository.findAll();

        StepVerifier.create(all)
                    .expectNextCount(1)
                    .verifyComplete();


    }


}
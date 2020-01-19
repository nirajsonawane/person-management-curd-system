package com.api.integration;

import com.api.person.enity.Person;
import com.api.person.repository.PersonRepository;
import com.api.util.TestUtil;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
@ExtendWith(RandomBeansExtension.class)
@ActiveProfiles(value = "integration")
class PersonControllerDeleteByIdTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        personRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should Delete Person with Id")
    void shouldDeletePerson() throws Exception {
        Person person1 = personRepository.save(TestUtil.getPerson()).block();

        webTestClient.delete()
                     .uri(URI.create("/person/" + person1.getPersonId()))
                     .exchange()
                     .expectStatus().isNoContent();

        Mono<Person> byId = personRepository.findById(person1.getPersonId());
        StepVerifier.create(byId).expectNextCount(0).verifyComplete();
    }

    @Test
    @DisplayName("Should Return 404 For Invalid ID")
    void shouldReturn404() throws Exception {
        webTestClient.delete()
                     .uri(URI.create("/person/" + "SomeWrongID"))
                     .exchange()
                     .expectStatus().isNotFound();

    }


}
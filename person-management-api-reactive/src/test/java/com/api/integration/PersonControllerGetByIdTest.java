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

import java.net.URI;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
@ExtendWith(RandomBeansExtension.class)
@ActiveProfiles(value = "integration")
class PersonControllerGetByIdTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        personRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should Return Person with Id")
    void shouldReturnPerson() throws Exception {
        Person person1 = personRepository.save(TestUtil.getPerson()).block();
        webTestClient.get()
                     .uri(URI.create("/person/" + person1.getPersonId()))
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody()
                     .jsonPath("$.first_name", person1.getFirstName());
    }

    @Test
    @DisplayName("Should Return 404 For Invalid ID")
    void shouldReturn404() throws Exception {
        webTestClient.get()
                     .uri(URI.create("/person/" + "SomeWrongID"))
                     .exchange()
                     .expectStatus().isNotFound();

    }


}
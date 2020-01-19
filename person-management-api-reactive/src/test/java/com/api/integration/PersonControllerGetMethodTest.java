package com.api.integration;

import com.api.person.enity.Person;
import com.api.person.model.CreatePersonRequest;
import com.api.person.repository.PersonRepository;
import com.api.util.TestUtil;
import io.github.glytching.junit.extension.random.Random;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
@ExtendWith(RandomBeansExtension.class)
@ActiveProfiles(value = "integration")
class PersonControllerGetMethodTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        personRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should Return All Person")
    void shouldReturnAllPerson() throws Exception {
        Person person1 = personRepository.save(TestUtil.getPerson()).block();
        Person person2 = personRepository.save(TestUtil.getPerson()).block();


        webTestClient.get()
                     .uri(URI.create("/person/"))
                     .exchange()
                     .expectStatus().isOk()
                     .expectBodyList(Person.class)
                     .contains(person1)
                     .contains(person2);

    }

    @Test
    @DisplayName("Should Return Empty Respons if No Data")
    void shouldReturnEmptyPerson() throws Exception {


        webTestClient.get()
                     .uri(URI.create("/person/"))
                     .exchange()
                     .expectStatus().isOk()
                     .expectBodyList(Person.class)
                     .hasSize(0)
        ;

    }


}
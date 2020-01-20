package com.api.integration;

import com.api.person.enity.Person;
import com.api.person.mapper.PersonMapper;
import com.api.person.model.CreatePersonRequest;
import com.api.person.model.GetPersonResponse;
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
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
@ExtendWith(RandomBeansExtension.class)
@ActiveProfiles(value = "integration")
class PersonControllerPutMethodTest {

    @Random
    private CreatePersonRequest updatePersonRequest;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;


    @BeforeEach
    public void setup() {
        personRepository
                .deleteAll()
                .block();
    }

    @Test
    @DisplayName("Should update Person For Valid Request")
    void shouldReturnUpdatePerson() throws Exception {

        GetPersonResponse block = personRepository
                .save(TestUtil.getPerson())
                .map(personMapper::fromPerson)
                .block();
        CreatePersonRequest updatedLastName = CreatePersonRequest
                .builder()
                .age(block.getAge())
                .favouriteColour(block.getFavouriteColour())
                .firstName(block.getFirstName())
                .lastName("UpdatedLastName")
                .hobby(block.getHobby())
                .build();
        webTestClient
                .put()
                .uri(URI.create("/person/"+block.getPersonId()))
                .body(Mono.just(updatedLastName), CreatePersonRequest.class)
                .exchange()
                .expectStatus()
                .isNoContent();

        Person fromDb = personRepository
                .findById(block.getPersonId())
                .block();

        assertEquals(updatedLastName.getLastName(), fromDb.getLastName());


    }

    @Test
    @DisplayName("Should get 404 For Wrong Person  id")
    void shouldReturn404FormWrongPersonId() throws Exception {
        webTestClient
                .put()
                .uri(URI.create("/person/"+"SomeWrongId"))
                .body(Mono.just(updatePersonRequest), CreatePersonRequest.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }


}
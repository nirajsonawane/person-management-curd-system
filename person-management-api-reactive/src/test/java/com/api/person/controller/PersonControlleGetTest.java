package com.api.person.controller;

import com.api.person.mapper.PersonMapper;
import com.api.person.service.PersonService;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@ExtendWith({SpringExtension.class, RandomBeansExtension.class})
@WebFluxTest(PersonController.class)
class PersonControlleGetTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonService personService;



    @MockBean
    private PersonMapper personMapper;


    @Test
    @DisplayName("Should Get Person with Id")
    void shouldGetPerson() throws Exception {
        Mockito.when(personService.getPersonById(Mockito.any())).thenReturn(Mono.just(ResponseEntity.ok().build()));
        webTestClient.delete()
                     .uri(URI.create("/person/" + "SomeID"))
                     .exchange()
                     .expectStatus().isOk();

    }

    @Test
    @DisplayName("Should return Internal Server Error For Server Exceptions")
    void shouldReturnInternalServerError() throws Exception {
        Mockito.when(personService.getPersonById(Mockito.any())).thenReturn(Mono.error(new RuntimeException("Mock Exception")));
        webTestClient.get()
                .uri(URI.create("/person/someID"))
                .exchange()
                .expectStatus().is5xxServerError();
    }




}
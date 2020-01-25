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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.URI;
@ExtendWith({SpringExtension.class, RandomBeansExtension.class})
@WebFluxTest(PersonController.class)
@ActiveProfiles(value = "test")

class PersonControllerDeleteByIdTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonService personService;



    @MockBean
    private PersonMapper personMapper;


    @Test
    @DisplayName("Should Delete Person with Id")
    void shouldDeletePerson() throws Exception {
        Mockito.when(personService.deletePersonById(Mockito.any())).thenReturn(Mono.just(ResponseEntity.noContent().build()));
        webTestClient.delete()
                     .uri(URI.create("/person/" + "SomeID"))
                     .exchange()
                     .expectStatus().isNoContent();

    }

    @Test
    @DisplayName("Should return Internal Server Error For Server Exceptions")
    void shouldReturnInternalServerError() throws Exception {
        Mockito.when(personService.deletePersonById(Mockito.any())).thenReturn(Mono.error(new RuntimeException("Mock Exception")));
        webTestClient.delete()
                .uri(URI.create("/person/someID"))
                .exchange()
                .expectStatus().is5xxServerError();
    }




}
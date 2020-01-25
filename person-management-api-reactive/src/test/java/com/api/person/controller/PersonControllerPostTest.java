package com.api.person.controller;

import com.api.person.mapper.PersonMapper;
import com.api.person.model.CreatePersonRequest;
import com.api.person.service.PersonService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@ExtendWith({SpringExtension.class, RandomBeansExtension.class})
@WebFluxTest(PersonController.class)
@ActiveProfiles(value = "test")

class PersonControllerPostTest {

    @Random
    private static CreatePersonRequest person;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;

    @Autowired
    private WebTestClient webTestClient;


    @DisplayName("Should Return Bad Request Error For Missing Attribute")
    @ParameterizedTest
    @MethodSource("com.api.util.TestUtil#createPersonWithMissingData")
    void shouldReturnBadRequestErrorForMissingAttributes(CreatePersonRequest personRequest) throws Exception {
        webTestClient.post()
                     .uri(URI.create("/person/"))
                     .body(Mono.just(personRequest), CreatePersonRequest.class)
                     .exchange()
                     .expectStatus().isBadRequest()
                     .expectBody()
                     .consumeWith(response ->
                             Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    @DisplayName("Should return isCreated For Valid Request")
    void shouldReturnIsCreated() throws Exception {
        webTestClient.post()
                     .uri(URI.create("/person/"))
                     .body(Mono.just(person), CreatePersonRequest.class)
                     .exchange()
                     .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Should return Internal Server Error For Server Exceptions")
    void shouldReturnInternalServerError() throws Exception {
        Mockito.when(personService.save(Mockito.any())).thenReturn(Mono.error(new RuntimeException("Mock Exception")));
        webTestClient.post()
                     .uri(URI.create("/person/"))
                     .body(Mono.just(person), CreatePersonRequest.class)
                     .exchange()
                     .expectStatus().is5xxServerError();
    }

}

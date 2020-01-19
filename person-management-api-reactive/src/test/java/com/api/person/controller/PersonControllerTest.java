package com.api.person.controller;

import com.api.person.enity.Person;
import com.api.person.mapper.PersonMapper;
import com.api.person.model.Colour;
import com.api.person.model.CreatePersonRequest;
import com.api.person.service.PersonService;
import com.api.util.TestUtil;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Stream;

@ExtendWith({SpringExtension.class, RandomBeansExtension.class})
@WebFluxTest(PersonController.class)
class PersonControllerTest {

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
    @MethodSource("createPersonWithMissingData")
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

    private static Stream<Arguments> createPersonWithMissingData() {
        CreatePersonRequest personWithNullAge = TestUtil.getCreatePersonRequest();
        personWithNullAge.setAge(null);
        CreatePersonRequest personWithNullColour = TestUtil.getCreatePersonRequest();
        personWithNullColour.setFavouriteColour(null);
        CreatePersonRequest personwithNullFirstName = TestUtil.getCreatePersonRequest();
        personwithNullFirstName.setFirstName(null);
        CreatePersonRequest personWithNullLastName = TestUtil.getCreatePersonRequest();
        personWithNullLastName.setLastName(null);
        CreatePersonRequest personWithNullHobby = TestUtil.getCreatePersonRequest();
        personWithNullHobby.setHobby(null);
        return Stream.of(Arguments.of(personWithNullAge),
                Arguments.of(personWithNullHobby),
                Arguments.of(personWithNullColour),Arguments.of(personwithNullFirstName),Arguments.of(personWithNullLastName));
    }




}

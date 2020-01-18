package com.api.person.controlller;

import com.api.person.controller.PersonController;
import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.exception.ResourceNotFoundException;
import com.api.person.mapper.PersonMapper;
import com.api.person.model.Colour;
import com.api.person.model.GetPersonResponse;
import com.api.person.service.PersonService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@ExtendWith({RandomBeansExtension.class, SpringExtension.class})
@Slf4j
class PersonControllerGetByIdTest {


    @Random
    private static Person person;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;

    @Autowired
    private MockMvc mvc;

    private static Stream<Arguments> verifyPersonIntAttributes() {
        Person person = getMockPerson();
        return Stream.of(
                Arguments.of("$.age", person.getAge())
        );
    }

    private static Stream<Arguments> verifyPersonStringAttributes() {
        Person person = getMockPerson();
        return Stream.of(Arguments.of("$.first_name", person.getFirstName())
                , Arguments.of("$.last_name", person.getLastName())
                , Arguments.of("$.favourite_colour", person
                        .getFavouriteColour()
                        .getColourValue())
        );
    }

    private static Stream<Arguments> verifyPersonListAttributes() {
        Person person = getMockPerson();
        return Stream.of(Arguments.of("$.hobby", person.getHobby())
        );
    }

    private static Person getMockPerson() {
        return Person
                .builder()
                .age(32)
                .favouriteColour(Colour.RED)
                .firstName("Niraj")
                .lastName("Sonawane")
                .hobby(Arrays.asList(new Hobby("Cricket")))
                .build();
    }

    private static GetPersonResponse getMockGetPersonResponse() {
        return GetPersonResponse
                .builder()
                .age(32)
                .favouriteColour(Colour.RED)
                .firstName("Niraj")
                .lastName("Sonawane")
                .hobby(Arrays.asList(("Cricket")))
                .build();
    }

    @ParameterizedTest(name = "Should Return Person #{index} with Value [{arguments}]")
    @MethodSource("verifyPersonStringAttributes")
    void verifyPersonStringAttributes(String jsonAttribute, String value) throws Exception {
        Mockito
                .when(personService.getPersonById(Mockito.anyInt()))
                .thenReturn(getMockPerson());

        Mockito
                .when(personMapper.fromPerson(Mockito.any()))
                .thenReturn(getMockGetPersonResponse());
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonAttribute).value(value));
    }

    @ParameterizedTest(name = "Should Return Person #{index} with Value [{arguments}]")
    @MethodSource("verifyPersonIntAttributes")
    void verifyPersonIntAttributes(String jsonAttribute, int value) throws Exception {
        Mockito
                .when(personService.getPersonById(Mockito.anyInt()))
                .thenReturn(getMockPerson());

        Mockito
                .when(personMapper.fromPerson(Mockito.any()))
                .thenReturn(getMockGetPersonResponse());

        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonAttribute).value(value));
    }

    @ParameterizedTest(name = "Should Return Person #{index} with Value [{arguments}]")
    @MethodSource("verifyPersonListAttributes")
    void verifyPersonListAttributes(String jsonAttribute, List<String> value) throws Exception {

        Mockito
                .when(personService.getPersonById(Mockito.anyInt()))
                .thenReturn(getMockPerson());

        Mockito
                .when(personMapper.fromPerson(Mockito.any()))
                .thenReturn(getMockGetPersonResponse());

        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonAttribute).isArray());
    }

    @Test
    @DisplayName("Should Return Internal Server Error")
    void shouldReturnInternalServerError() throws Exception {

        Mockito
                .when(personService.getPersonById(Mockito.anyInt()))
                .thenThrow(new RuntimeException("Mock Exception"));
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Mock Exception")))
                .andExpect(status().isInternalServerError());

    }

    @Test
    @DisplayName("Should Return resource not found")
    void shouldReturnResourceNotFound() throws Exception {

        Mockito
                .when(personService.getPersonById(Mockito.anyInt()))
                .thenThrow(new ResourceNotFoundException("Not Found"));
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Not Found")))
                .andExpect(status().isNotFound());

    }


}
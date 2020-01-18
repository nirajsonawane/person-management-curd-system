package com.api.person.controlller;

import com.api.person.controller.PersonController;
import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@ExtendWith({RandomBeansExtension.class, SpringExtension.class})
@Slf4j
class PersonControllerGetAllTest {


    @Random
    private static Person person;

    @Random
    private static List<Person> personList;

    @Random
    private static GetPersonResponse getPersonResponse;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;

    @Autowired
    private MockMvc mvc;

    private static Person getMockPerson() {
        return Person
                .builder()
                .age(32)
                .favouriteColour(Colour.RED)
                .firstName("Niraj")
                .lastName("Sonawane")
                .hobby(Arrays.asList(Hobby.builder().hobbyStringValue("Cricket").build()))
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

    @Test
    @DisplayName("Should Return List Of Persons")
    void getAllPerson() throws Exception {
        Mockito
                .when(personService.getAllPersons())
                .thenReturn(Arrays.asList(person, person));

        Mockito
                .when(personMapper.fromPerson(Mockito.any()))
                .thenReturn(getPersonResponse);

        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should Return Empty List if No Persons in Database")
    void getAllPersonEmptyResponse() throws Exception {

        Mockito
                .when(personService.getAllPersons())
                .thenReturn(new ArrayList<>());

        Mockito
                .when(personMapper.fromPerson(Mockito.any()))
                .thenReturn(getPersonResponse);

        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should Return Internal Server Error")
    void getAllPersonWithException() throws Exception {
        Mockito
                .when(personService.getAllPersons())
                .thenThrow(new RuntimeException("Mock Exception"));
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Mock Exception")))
                .andExpect(status().isInternalServerError());
    }

}
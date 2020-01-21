package com.api.person.controlller;

import com.api.common.exception.ResourceNotFoundException;
import com.api.person.controller.PersonController;
import com.api.person.mapper.PersonMapper;

import com.api.person.model.PersonRequest;
import com.api.person.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith({RandomBeansExtension.class, SpringExtension.class})
@Slf4j
class PersonControllerPutMethodTest {


    @Random
    private static PersonRequest person;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;

    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("Should Return Bad Request Error For Missing Age")
    void shouldReturnBadRequestErrorForMissingAge() throws Exception {
        person.setAge(null);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                .andExpect(content().string(containsString("Age is mandatory")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Hobby")
    void shouldReturnBadRequestErrorForMissingHobby() throws Exception {
        person.setHobby(null);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("At Lest On hobbyStringValue is needed")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Empty Hobby List")
    void shouldReturnBadRequestErrorForMissingEmptyHobbyList() throws Exception {
        person.setHobby(new ArrayList<String>());
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("At Lest On hobbyStringValue is needed")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Null First Name ")
    void shouldReturnBadRequestErrorForMissingFirstName() throws Exception {
        person.setFirstName(null);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("First Name is mandatory")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Null First Name ")
    void shouldReturnBadRequestErrorForEmptyFirstName() throws Exception {
        person.setFirstName("");
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("First Name is mandatory")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Null Last Name ")
    void shouldReturnBadRequestErrorForMissingLastName() throws Exception {
        person.setLastName(null);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Last Name is mandatory")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Null Last Name ")
    void shouldReturnBadRequestErrorForEmptyLastName() throws Exception {
        person.setLastName("");
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Last Name is mandatory")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should Return Bad Request Error For Null favourite_colour ")
    void shouldReturnBadRequestErrorForNullFavouriteColour() throws Exception {
        person.setFavouriteColour(null);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Favourite colour is mandatory")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return isNoContent For Valid Request")
    void shouldReturnisNoContent() throws Exception {
        Mockito.doNothing().when(personService).update(Mockito.any(),Mockito.anyLong());
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return Internal Server Error  For Exception")
    void shouldReturnisInteralServerErrorContent() throws Exception {
        Mockito.doThrow(RuntimeException.class).when(personService).update(Mockito.any(),Mockito.anyLong());
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/1234")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
    @Test
    @DisplayName("Should return 404 for invalid person is")
    void shouldReturnis404() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(personService).update(Mockito.any(),Mockito.anyLong());
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person/123456")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }



    public static String asJsonString(final Object obj) {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(obj));
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
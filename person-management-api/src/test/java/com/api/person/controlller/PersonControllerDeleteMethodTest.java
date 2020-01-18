package com.api.person.controlller;

import com.api.person.controller.PersonController;
import com.api.person.exception.ResourceNotFoundException;
import com.api.person.mapper.PersonMapper;
import com.api.person.service.PersonService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ExtendWith({RandomBeansExtension.class, SpringExtension.class})
@Slf4j
class PersonControllerDeleteMethodTest {


    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should Return no content For Successful Deletion")
    void shouldReturnNoContent() throws Exception {

        Mockito
                .doNothing()
                .when(personService)
                .delete(Mockito.anyInt());
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should Return 404  For Person Not Found")
    void shouldReturnNotFound() throws Exception {
        Mockito
                .doThrow(ResourceNotFoundException.class)
                .when(personService)
                .delete(Mockito.anyInt());
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Return Internal Server Error For Exception")
    void shouldReturnInternalServerError() throws Exception {
        Mockito
                .doThrow(RuntimeException.class)
                .when(personService)
                .delete(Mockito.anyInt());
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
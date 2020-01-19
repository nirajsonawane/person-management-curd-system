package com.api.integration;

import com.api.person.controller.PersonController;
import com.api.person.enity.Person;
import com.api.person.mapper.PersonMapper;
import com.api.person.model.CreatePersonRequest;
import com.api.person.model.UpdatePersonRequest;
import com.api.person.repository.PersonRepository;
import com.api.person.service.PersonService;
import com.api.security.entity.User;
import com.api.security.model.AuthenticateRequest;
import com.api.security.model.JwtAuthenticationResponse;
import com.api.security.repository.UserRepository;
import com.api.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "integration")
@AutoConfigureMockMvc
@Slf4j
@ExtendWith(RandomBeansExtension.class)
class PersonControllerPostMethodTest {

    @Random
    private CreatePersonRequest person;

    @Random
    private UpdatePersonRequest updatePersonRequest;
    @Random
    private Person mockPerson;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        TestUtil.setupJwtUser(userRepository);
        TestUtil.cleanData(personRepository);
    }


    @Test
    @DisplayName("Should Accept request with valid jwt Token and create user")
    void shouldReturnIsCreatedAndSavePersonInDatabase() throws Exception {

        String token = TestUtil.getToken(mvc);
        mvc
                .perform(MockMvcRequestBuilders
                        .post("/person")
                        .header("Authorization", "Bearer " + token)
                        .content(TestUtil.asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        assertFalse(personRepository
                .findAll()
                .isEmpty());
    }




}
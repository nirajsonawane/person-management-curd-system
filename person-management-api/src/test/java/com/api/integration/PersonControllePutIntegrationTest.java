package com.api.integration;

import com.api.person.enity.Person;
import com.api.person.model.UpdatePersonRequest;
import com.api.person.repository.PersonRepository;
import com.api.security.repository.UserRepository;
import com.api.util.TestUtil;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "integration")
@AutoConfigureMockMvc
@Slf4j
@ExtendWith(RandomBeansExtension.class)
class PersonControllePutIntegrationTest {


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
    @DisplayName("Should Accept request with valid jwt Token and update user")
    void shouldUpdatePersonInDatabase() throws Exception {
        Person save = personRepository.save(mockPerson);
        personRepository.flush();


        updatePersonRequest.setFirstName("Test user");
        updatePersonRequest.setPersonId(save.getPersonId());

        String token = TestUtil.getToken(mvc);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person")
                        .header("Authorization", "Bearer " + token)
                        .content(TestUtil.asJsonString(updatePersonRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        personRepository.flush();
        Person byId = personRepository
                .findById(save.getPersonId())
                .get();
        assertEquals(byId.getFirstName(), "Test user");

    }

}
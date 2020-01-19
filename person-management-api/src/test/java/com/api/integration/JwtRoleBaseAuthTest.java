package com.api.integration;

import com.api.person.enity.Person;
import com.api.person.repository.PersonRepository;
import com.api.security.repository.UserRepository;
import com.api.util.TestUtil;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "integration")
@AutoConfigureMockMvc
@ExtendWith(RandomBeansExtension.class)
public class JwtRoleBaseAuthTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Random
    private Person mockPerson;

    @BeforeEach
    public void setup() {
        TestUtil.setupJwtUser(userRepository);
        TestUtil.cleanData(personRepository);
    }

    @Test
    @DisplayName("Only User With Admin Role should able to call delete person API")
    void shouldAbleToRunDeleteMethod() throws Exception {
        Person save = personRepository.save(mockPerson);
        personRepository.flush();
        String token = TestUtil.getToken(mvc);
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/person/" + save.getPersonId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }
    @Test
    @DisplayName("Valid Non Admin user should not able to call delete person API")
    void shouldNotAbleToRunDeleteMethod() throws Exception {
        Person save = personRepository.save(mockPerson);
        personRepository.flush();
        String token = TestUtil.getTokenForNonAdminUser(mvc);
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/person/" + save.getPersonId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
}

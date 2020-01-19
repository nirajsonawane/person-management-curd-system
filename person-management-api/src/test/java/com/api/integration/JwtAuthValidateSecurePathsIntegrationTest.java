package com.api.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class JwtAuthValidateSecurePathsIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should get isForbidden without Token For get Person")
    void shouldGetIsForbiddenForGetPerson() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
    @Test
    @DisplayName("Should get isForbidden without Token Get Person With ID")
    void shouldGetIsForbiddenForGetPersonWithId() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
    @Test
    @DisplayName("Should get isForbidden without Token Put Person With ID")
    void shouldGetIsForbiddenForPutPerson() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
    @Test
    @DisplayName("Should get isForbidden without Token For Put method")
    void shouldGetIsForbiddenForDeletePerson() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
    @Test
    @DisplayName("Should get isForbidden without Token For Post method")
    void shouldGetIsForbiddenForPostPerson() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders
                        .post("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

}

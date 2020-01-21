package com.api.util;

import com.api.person.model.Colour;
import com.api.person.model.PersonRequest;
import com.api.person.repository.PersonRepository;
import com.api.security.entity.User;
import com.api.security.model.AuthenticateRequest;
import com.api.security.model.JwtAuthenticationResponse;
import com.api.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TestUtil {

    public static void main(String[] args) {
        PersonRequest createPersonRequest = PersonRequest.builder().age(31)
                                                         .favouriteColour(Colour.BLACK)
                                                         .firstName("Niraj")
                                                         .lastName("Sonawane")
                                                         .hobby(Arrays.asList("Cricket")).build();
        System.out.println(asJsonString(createPersonRequest));
    }
    public static String asJsonString(final Object obj) {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(obj));
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static JwtAuthenticationResponse getObjectFromString(final String  obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return  objectMapper.readValue(obj, JwtAuthenticationResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getToken(MockMvc mockMvc) throws Exception {
        MvcResult test = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/authenticate")
                        .content(TestUtil.asJsonString(new AuthenticateRequest("niraj.sonawane@gmail.com", "test")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        String content = test.getResponse().getContentAsString();
        JwtAuthenticationResponse objectFromString = TestUtil.getObjectFromString(content);
        return  objectFromString.getAccessToken();
    }
    public static String getTokenForNonAdminUser(MockMvc mockMvc) throws Exception {
        MvcResult test = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/authenticate")
                        .content(TestUtil.asJsonString(new AuthenticateRequest("text@test.com", "test")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        String content = test.getResponse().getContentAsString();
        JwtAuthenticationResponse objectFromString = TestUtil.getObjectFromString(content);
        return  objectFromString.getAccessToken();
    }
    public static void setupJwtUser(UserRepository userRepository) {
        User niraj = new User("niraj.sonawane@gmail.com", "$2a$10$yRxRYK/s8vZCp.bgmZsD/uXmHjekuPU/duM0iPZw04ddt1ID9H7kK", "ROLE_ADMIN");
        User nonAdmin = new User("text@test.com", "$2a$10$yRxRYK/s8vZCp.bgmZsD/uXmHjekuPU/duM0iPZw04ddt1ID9H7kK", "ROLE_USER");
        userRepository.save(nonAdmin);
        userRepository.save(niraj);
        userRepository.flush();

    }
    public static void cleanData(PersonRepository personRepository) {
        personRepository.deleteAll();
        personRepository.flush();

    }
}

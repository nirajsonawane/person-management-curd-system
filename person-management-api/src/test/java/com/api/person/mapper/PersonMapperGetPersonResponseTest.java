package com.api.person.mapper;

import com.api.person.enity.Person;
import com.api.person.model.GetPersonResponse;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(RandomBeansExtension.class)
class PersonMapperGetPersonResponseTest {

    @Autowired
    private PersonMapper personMapper;

    @Random
    private Person mockPerson;

    @Test
    @DisplayName("Should Convert Domain Person Object To Response Object")
    void fromPerson() {
        GetPersonResponse getPersonResponse = personMapper.fromPerson(mockPerson);
        assertEquals(mockPerson.getAge(), getPersonResponse.getAge());
        assertEquals(mockPerson
                .getFavouriteColour()
                .getColourValue(), getPersonResponse
                .getFavouriteColour()
                .getColourValue());
        assertEquals(mockPerson.getFirstName(), getPersonResponse.getFirstName());
        assertEquals(mockPerson.getLastName(), getPersonResponse.getLastName());

    }

    @Test
    @DisplayName("Should Convert Domain Hobby Object To Response String Hobby Object")
    void FromHobby() {
        GetPersonResponse person = personMapper.fromPerson(mockPerson);
        assertEquals(mockPerson
                .getHobby()
                .size(), person
                .getHobby()
                .size());


    }

    @Test
    @DisplayName("Should Convert Domain Hobby Object To Response String Hobby Object - Null Check")
    void FromHobby_NullCheck() {
        mockPerson.setHobby(null);
        GetPersonResponse person = personMapper.fromPerson(mockPerson);
        assertTrue(person
                .getHobby()
                .isEmpty());

    }

}
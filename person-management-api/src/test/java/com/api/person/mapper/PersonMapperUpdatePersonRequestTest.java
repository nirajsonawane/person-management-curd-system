package com.api.person.mapper;

import com.api.person.enity.Person;
import com.api.person.model.UpdatePersonRequest;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(RandomBeansExtension.class)
class PersonMapperUpdatePersonRequestTest {

    @Autowired
    private PersonMapper personMapper;


    @Random
    private UpdatePersonRequest updatePersonRequest;


    @Test
    @DisplayName("Should Convert to Domain Person Object From updatePersonRequest")
    void toPerson() {

        Person person = personMapper.toPerson(updatePersonRequest);
        assertEquals(person.getPersonId(), updatePersonRequest.getPersonId());
        assertEquals(person.getAge(), updatePersonRequest.getAge());
        assertEquals(person
                .getFavouriteColour()
                .getColourValue(), updatePersonRequest
                .getFavouriteColour()
                .getColourValue());
        assertEquals(person.getFirstName(), updatePersonRequest.getFirstName());
        assertEquals(person.getLastName(), updatePersonRequest.getLastName());

    }


}
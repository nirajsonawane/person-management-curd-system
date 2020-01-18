package com.api.person.mapper;

import com.api.person.enity.Person;
import com.api.person.model.CreatePersonRequest;
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
class PersonMapperCreatePersonRequestTest {

    @Autowired
    private PersonMapper personMapper;


    @Random
    private CreatePersonRequest createPersonRequest;


    @Test
    @DisplayName("Should Convert to Domain Person Object From createPersonRequest")
    void toPerson() {

        Person person = personMapper.toPerson(createPersonRequest);
        assertEquals(person.getAge(),createPersonRequest.getAge());
        assertEquals(person.getFavouriteColour().getColourValue(),createPersonRequest.getFavouriteColour().getColourValue());
        assertEquals(person.getFirstName(),createPersonRequest.getFirstName());
        assertEquals(person.getLastName(),createPersonRequest.getLastName());

    }


    @Test
    @DisplayName("Should Convert To Domain Hobby  Object From String")
    void toHobbyValue() {
        Person person = personMapper.toPerson(createPersonRequest);
        assertEquals(createPersonRequest.getHobby().size(),person.getHobby().size());
        String sourceHobby = createPersonRequest
                .getHobby()
                .get(0);
        String destinationHobby = person.getHobby().get(0).getHobbyStringValue();
        assertEquals(sourceHobby,destinationHobby);
    }

    @Test
    @DisplayName("Should Convert Domain Person Object To Response Object")
    void toHobby() {
        Person person = personMapper.toPerson(createPersonRequest);
        assertEquals(createPersonRequest.getHobby().size(),person.getHobby().size());
    }

    @Test
    @DisplayName("Should Convert Domain Person Object To Response Object-Null Check")
    void toHobby_NullCheck() {
        createPersonRequest.setHobby(null);
        Person person = personMapper.toPerson(createPersonRequest);
        assertTrue(person.getHobby().isEmpty());
    }

}
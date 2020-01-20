package com.api.util;

import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.model.Colour;
import com.api.person.model.CreatePersonRequest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.stream.Stream;

public class TestUtil {
    public static CreatePersonRequest getCreatePersonRequest() {
        return CreatePersonRequest.builder().age(31).favouriteColour(Colour.BLACK).firstName("Niraj")
                                  .hobby(Arrays.asList("Cricket")).lastName("Sonawane").build();
    }

    public static Person getPerson() {
        return Person.builder().age(31).favouriteColour(Colour.BLACK).firstName("Niraj")
                     .lastName("Sonawane")
                     .hobby(Arrays.asList(Hobby.builder().hobbyName("Cricket").build())).build();
    }
    public static Stream<Arguments> createPersonWithMissingData() {
        CreatePersonRequest personWithNullAge = TestUtil.getCreatePersonRequest();
        personWithNullAge.setAge(null);
        CreatePersonRequest personWithNullColour = TestUtil.getCreatePersonRequest();
        personWithNullColour.setFavouriteColour(null);
        CreatePersonRequest personwithNullFirstName = TestUtil.getCreatePersonRequest();
        personwithNullFirstName.setFirstName(null);
        CreatePersonRequest personWithNullLastName = TestUtil.getCreatePersonRequest();
        personWithNullLastName.setLastName(null);
        CreatePersonRequest personWithNullHobby = TestUtil.getCreatePersonRequest();
        personWithNullHobby.setHobby(null);
        return Stream.of(Arguments.of(personWithNullAge),
                Arguments.of(personWithNullHobby),
                Arguments.of(personWithNullColour),Arguments.of(personwithNullFirstName),Arguments.of(personWithNullLastName));
    }
}

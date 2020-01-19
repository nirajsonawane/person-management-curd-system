package com.api.util;

import com.api.person.enity.Hobby;
import com.api.person.enity.Person;
import com.api.person.model.Colour;
import com.api.person.model.CreatePersonRequest;

import java.util.Arrays;

public class TestUtil {
    public static CreatePersonRequest getCreatePersonRequest() {
        return CreatePersonRequest.builder().age(31).favouriteColour(Colour.BLACK).firstName("Niraj")
                                  .hobby(Arrays.asList("Cricket")).lastName("Sonawane").build();
    }

    public static Person getPerson() {
        return Person.builder().age(31).favouriteColour(Colour.BLACK).firstName("Niraj")
                     .lastName("Sonawane")
                     .hobby(Arrays.asList(Hobby.builder().hobbyStringValue("Cricket").build())).build();
    }
}

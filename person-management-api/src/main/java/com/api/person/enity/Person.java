package com.api.person.enity;

import com.api.person.model.Colour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Person {


    private  Long personId;
    private String firstName;
    private String lastName;
    private Integer age;
    private Colour favouriteColour;
    private List<Hobby> hobby;
}

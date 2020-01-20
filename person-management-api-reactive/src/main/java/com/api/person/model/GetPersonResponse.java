package com.api.person.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetPersonResponse {

    @JsonProperty("id")
    private String personId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Integer age;

    @JsonProperty("favourite_colour")
    private Colour favouriteColour;

    private List<String> hobby;
}

package com.api.person.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePersonRequest implements Serializable{


    @NotNull(message = "Person Id is mandatory")
    @JsonProperty("id")
    private Long personId;

    @NotEmpty(message = "First Name is mandatory")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last Name is mandatory")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Age is mandatory")
    private Integer age;

    @NotNull(message = "Favourite colour is mandatory")
    @JsonProperty("favourite_colour")
    private Colour favouriteColour;

    @NotEmpty(message = "At Lest On hobbyStringValue is needed")
    private List<String> hobby;
}

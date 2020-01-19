package com.api.person.enity;

import com.api.person.model.Colour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "person")
public class Person {

    @Id
    private String personId;
    private String firstName;
    private String lastName;
    private Integer age;
    private Colour favouriteColour;
    private List<Hobby> hobby;
}

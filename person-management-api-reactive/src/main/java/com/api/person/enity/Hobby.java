package com.api.person.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
public class Hobby {

  /*  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Long hobbyId;
    private String hobbyStringValue;


}

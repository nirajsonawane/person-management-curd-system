package com.api.person.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Colour {

    RED("red"),GREEN("green"),BLUE("blue"),BLACK("black"),WHITE("white");

    @JsonValue
    private final String colourValue;

    private  Colour(String colourText){
        this.colourValue =colourText;
    }
}

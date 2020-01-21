package com.api.person.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Colour {

    RED("red"),GREEN("green"),BLUE("blue"),BLACK("back"),WHITE("white");

    @JsonValue
    private final String colourValue;

    Colour(String colourText){
        this.colourValue =colourText;
    }
}

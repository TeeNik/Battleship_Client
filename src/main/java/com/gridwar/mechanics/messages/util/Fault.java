package com.gridwar.mechanics.messages.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

public class Fault extends Message {
    @JsonCreator
    public Fault(@JsonProperty("errorMessage") String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Getter @Setter
    private String errorMessage;
}

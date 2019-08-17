package com.gridwar.mechanics.messages.input.login;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

public class Login extends Message {

    @JsonCreator
    public Login(@JsonProperty("IMEI") String IMEI) {
        this.IMEI = IMEI;
    }

    @Getter @Setter
    private String IMEI;
}

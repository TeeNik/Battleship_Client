package com.gridwar.mechanics.messages.login;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

public class LoginInput extends Message {

    @JsonCreator
    public LoginInput(@JsonProperty("IMEI") String IMEI) {
        this.IMEI = IMEI;
    }

    @Getter @Setter
    private String IMEI;
}

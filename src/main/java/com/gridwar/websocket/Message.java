package com.gridwar.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gridwar.mechanics.messages.joingame.JoinGameInput;
import com.gridwar.mechanics.messages.login.LoginInput;
import com.gridwar.mechanics.messages.util.ServerTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
//@JsonSubTypes(value = {
//        @JsonSubTypes.Type(JoinGameInput.class),
//        @JsonSubTypes.Type(LoginInput.class),
//        @JsonSubTypes.Type(ServerTime.class)
//})
@AllArgsConstructor
public class Message {
    @Getter @Setter
    private Integer result;

    @Getter @Setter
    private String cmd;

    public Message(){
        result = 0;
    }
}

package com.gridwar.websocket;

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
@NoArgsConstructor
public class Message {

    @Getter @Setter
    private String cmd;

}

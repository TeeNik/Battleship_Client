package com.gridwar.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gridwar.mechanics.messages.input.joingame.JoinGame;
import com.gridwar.mechanics.messages.input.login.Login;
import com.gridwar.mechanics.messages.output.ServerTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(JoinGame.class),
        @JsonSubTypes.Type(Login.class),
        @JsonSubTypes.Type(ServerTime.class)
})
public abstract class Message {}

package com.gridwar.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gridwar.mechanics.messages.input.joingame.JoinGame;
import com.gridwar.mechanics.messages.input.login.Login;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(JoinGame.class),
        @JsonSubTypes.Type(Login.class)
})
public abstract class Message {}

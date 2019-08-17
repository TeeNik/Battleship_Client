package com.gridwar.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gridwar.mechanics.messages.input.JoinGame;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(JoinGame.class)
})
public abstract class Message {}

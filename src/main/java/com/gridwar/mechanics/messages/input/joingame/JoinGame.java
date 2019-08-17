package com.gridwar.mechanics.messages.input.joingame;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

public class JoinGame extends Message {

    @JsonCreator
    public JoinGame(@JsonProperty("players") Integer players) {
        this.players = players;
    }

    @Getter @Setter
    private Integer players;
}

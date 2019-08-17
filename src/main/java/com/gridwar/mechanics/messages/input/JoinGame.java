package com.gridwar.mechanics.messages.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gridwar.websocket.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(onConstructor=@_({@JsonCreator}))
public class JoinGame extends Message {
    @Getter @Setter
    private int players;
}

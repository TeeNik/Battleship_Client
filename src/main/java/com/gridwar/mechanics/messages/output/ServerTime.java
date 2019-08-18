package com.gridwar.mechanics.messages.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ServerTime extends Message {

    @Getter @Setter
    private LocalDateTime localDateTime;

    @JsonCreator
    public ServerTime(@JsonProperty("localDateTime") LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}

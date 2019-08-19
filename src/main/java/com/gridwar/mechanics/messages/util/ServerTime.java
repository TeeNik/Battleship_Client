package com.gridwar.mechanics.messages.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ServerTime extends Message {

    @Getter @Setter
    private LocalDateTime localDateTime;

    @Getter
    private final String HEADER = "server_time";

    @JsonCreator
    public ServerTime(@JsonProperty("localDateTime") LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}

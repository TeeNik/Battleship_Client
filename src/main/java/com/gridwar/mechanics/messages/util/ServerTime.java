package com.gridwar.mechanics.messages.util;

import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

public class ServerTime extends Message {

    @Getter @Setter
    private long time;

    @Getter
    private final String HEADER = "serverTime";

    public ServerTime() {
        time = System.currentTimeMillis();
    }
}

package com.gridwar.game.message;

import com.gridwar.websocket.Message;
import lombok.Data;

@Data
public class GameMessage extends Message {
    private String cmd;
    private Object params;
}

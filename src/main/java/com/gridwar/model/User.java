package com.gridwar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
public class User {
    @Getter @Setter private String IMEI;
    @Getter @Setter private WebSocketSession session;
}

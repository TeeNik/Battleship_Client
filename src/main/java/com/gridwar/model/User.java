package com.gridwar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
public class User {
    @NotNull
    @Getter @Setter private String IMEI;
    @NotNull
    @Getter @Setter private WebSocketSession session;
}

package com.gridwar.websocket;

import org.springframework.web.socket.TextMessage;

import javax.validation.constraints.NotNull;

public interface MessageHandlerContainer {

    void handle(@NotNull TextMessage textMessage, @NotNull String header, @NotNull String sessionId) throws HandleException;

    <T extends Message> void registerHandler(@NotNull String header, MessageHandler<T> handler);
}
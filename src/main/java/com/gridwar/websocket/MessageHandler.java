package com.gridwar.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.TextMessage;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@AllArgsConstructor
public abstract class MessageHandler <T extends Message> {

    private final @NotNull Class<T> clazz;

    private final ObjectMapper objectMapper;

    public void handleMessage(@NotNull TextMessage textMessage, @NotNull String sessionId) throws HandleException {
        try {
            T message = objectMapper.readValue(textMessage.getPayload(), clazz);
            handle(message, sessionId);
        } catch (IOException e) {
            throw new HandleException("incorrect data format:" + textMessage.getPayload());
        }

    }

    public abstract void handle(@NotNull T message, @NotNull String sessionId) throws HandleException;
}

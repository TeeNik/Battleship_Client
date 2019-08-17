package com.gridwar.websocket;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public abstract class MessageHandler <T extends Message> {

    private final @NotNull Class<T> clazz;

    public void handleMessage(@NotNull Message message, @NotNull String user) throws HandleException {
        try {
            handle(clazz.cast(message), user);
        } catch (ClassCastException ex) {
            throw new HandleException("Can't read incoming message of type " + message.getClass(), ex);
        }
    }

    public abstract void handle(@NotNull T message, @NotNull String forUser) throws HandleException;
}

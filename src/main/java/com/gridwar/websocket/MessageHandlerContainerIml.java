package com.gridwar.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageHandlerContainerIml implements MessageHandlerContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerContainerIml.class);
    private final Map<String, MessageHandler<?>> handlerMap = new HashMap<>();

    @Override
    public void handle(@NotNull TextMessage textMessage, @NotNull String header, @NotNull String sessionId) throws HandleException {

        final MessageHandler<?> messageHandler = handlerMap.get(header);
        if (messageHandler == null) {
            throw new HandleException("no handler for message of " + header + " type");
        }
        messageHandler.handleMessage(textMessage, sessionId);
        LOGGER.trace("message handled: type =[" + header + ']');
    }

    @Override
    public <T extends Message> void registerHandler(@NotNull String header, MessageHandler<T> handler) {
        handlerMap.put(header, handler);
    }
}

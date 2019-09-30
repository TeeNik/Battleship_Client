package com.gridwar.mechanics.messages.joingame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridwar.mechanics.UserQueueService;
import com.gridwar.websocket.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class JoinGameHandler extends MessageHandler<JoinGameInput> {
    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final SocketUserService socketUserService;
    @NotNull
    private final ObjectMapper mapper;

    private final String HEADER = "joingame";

    public JoinGameHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull SocketUserService socketUserService, @NotNull ObjectMapper mapper) {
        super(JoinGameInput.class, mapper);
        this.userQueueService = userQueueService;
        this.messageHandlerContainer = messageHandlerContainer;
        this.socketUserService = socketUserService;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(HEADER, this);
    }

    @Override
    public void handle(@NotNull JoinGameInput message, @NotNull String sessionId) throws HandleException {
        if (socketUserService.checkUserWasLogged(sessionId)) {
            userQueueService.addUserToQueue(sessionId);
            socketUserService.sendMessageToUserBySessionId(sessionId, new Message());
        } else {
            throw new HandleException("Forbidden operation");
        }
    }
}

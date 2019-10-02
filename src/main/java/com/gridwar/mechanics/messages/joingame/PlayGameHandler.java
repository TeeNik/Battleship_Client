package com.gridwar.mechanics.messages.joingame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridwar.mechanics.UserQueueService;
import com.gridwar.websocket.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class PlayGameHandler extends MessageHandler<PlayGameInput> {
    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final SocketUserService socketUserService;
    @NotNull
    private final ObjectMapper mapper;

    private final String HEADER = "playGame";

    public PlayGameHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull SocketUserService socketUserService, @NotNull ObjectMapper mapper) {
        super(PlayGameInput.class, mapper);
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
    public void handle(@NotNull PlayGameInput message, @NotNull String sessionId) throws HandleException {
        if (socketUserService.checkUserWasLogged(sessionId)) {
            userQueueService.addUserToQueue(sessionId);
            socketUserService.sendMessageToUserBySessionId(sessionId, new ResponseMessage(HEADER, 0));
        } else {
            throw new HandleException("Forbidden operation");
        }
    }
}

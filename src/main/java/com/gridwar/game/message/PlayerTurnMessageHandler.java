package com.gridwar.game.message;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridwar.game.GameSession;
import com.gridwar.mechanics.UserQueueService;
import com.gridwar.model.User;
import com.gridwar.websocket.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

public class PlayerTurnMessageHandler extends MessageHandler<PlayerTurnInput> {

    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final SocketUserService socketUserService;
    @NotNull
    private final ObjectMapper mapper;

    private final String HEADER = "playerTurn";


    public PlayerTurnMessageHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull SocketUserService socketUserService, @NotNull ObjectMapper mapper) {
        super(PlayerTurnInput.class, mapper);
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
    public void handle(@NotNull PlayerTurnInput message, @NotNull String sessionId) throws HandleException {
        User user = socketUserService.getUserBySessionId(sessionId);
        GameSession game = userQueueService.getGameSessionByUser(user);
        game.playerTurn(user, message);
    }
}

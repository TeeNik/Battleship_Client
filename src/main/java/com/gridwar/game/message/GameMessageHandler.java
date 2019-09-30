package com.gridwar.game.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridwar.game.GameSession;
import com.gridwar.mechanics.UserQueueService;
import com.gridwar.model.User;
import com.gridwar.websocket.HandleException;
import com.gridwar.websocket.MessageHandler;
import com.gridwar.websocket.MessageHandlerContainer;
import com.gridwar.websocket.SocketUserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class GameMessageHandler extends MessageHandler<GameMessage> {

    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final SocketUserService socketUserService;
    @NotNull
    private final ObjectMapper mapper;

    private final String HEADER = "cmd";

    public GameMessageHandler(ObjectMapper objectMapper, @NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull SocketUserService socketUserService, @NotNull ObjectMapper mapper) {
        super(GameMessage.class, objectMapper);
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
    public void handle(@NotNull GameMessage message, @NotNull String sessionId) throws HandleException {
        User user = socketUserService.getUserBySessionId(sessionId);
        GameSession gameSession = userQueueService.getGamseSessionByUser(user);
        gameSession.processMessage(user, message);
    }
}

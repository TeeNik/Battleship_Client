package com.gridwar.mechanics.messages.input.joingame;

import com.gridwar.mechanics.UserQueueService;
import com.gridwar.websocket.MessageHandler;
import com.gridwar.websocket.MessageHandlerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class JoinGameHandler extends MessageHandler<JoinGame> {
    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;

    public JoinGameHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGame.class);
        this.userQueueService = userQueueService;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGame.class, this);
    }

    @Override
    public void handle(@NotNull JoinGame message, @NotNull String sessionId) {
        userQueueService.addUserToQueue(sessionId);
    }
}

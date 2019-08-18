package com.gridwar.mechanics.messages.joingame;

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

    public JoinGameHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull SocketUserService socketUserService) {
        super(JoinGameInput.class);
        this.userQueueService = userQueueService;
        this.messageHandlerContainer = messageHandlerContainer;
        this.socketUserService = socketUserService;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGameInput.class, this);
    }

    @Override
    public void handle(@NotNull JoinGameInput message, @NotNull String sessionId) throws HandleException {
        if (socketUserService.checkUserWasLogged(sessionId)) {
            userQueueService.addUserToQueue(sessionId);
            socketUserService.sendMessageToUser(sessionId, new Message());
        } else {
            throw new HandleException("Forbidden operation");
        }
    }
}

package com.gridwar.mechanics.messages.input.joingame;

import com.gridwar.mechanics.GameManager;
import com.gridwar.websocket.MessageHandler;
import com.gridwar.websocket.MessageHandlerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class JoinGameHandler extends MessageHandler<JoinGame> {
    @NotNull
    private final GameManager gameManager;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;

    public JoinGameHandler(@NotNull GameManager gameManager, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGame.class);
        this.gameManager = gameManager;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGame.class, this);
    }

    @Override
    public void handle(@NotNull JoinGame message, @NotNull String sessionId) {
        gameManager.addUserToQueue(sessionId);
    }
}

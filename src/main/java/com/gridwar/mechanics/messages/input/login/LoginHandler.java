package com.gridwar.mechanics.messages.input.login;

import com.gridwar.mechanics.UserQueueService;
import com.gridwar.websocket.HandleException;
import com.gridwar.websocket.MessageHandler;
import com.gridwar.websocket.MessageHandlerContainer;
import com.gridwar.websocket.RemotePointService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class LoginHandler extends MessageHandler<Login> {

    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final RemotePointService remotePointService;

    public LoginHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull RemotePointService remotePointService) {
        super(Login.class);
        this.userQueueService = userQueueService;
        this.messageHandlerContainer = messageHandlerContainer;
        this.remotePointService = remotePointService;
    }

    @PostConstruct
    private void registerHandler() {
        messageHandlerContainer.registerHandler(Login.class, this);
    }

    @Override
    public void handle(@NotNull Login message, @NotNull String sessionId) throws HandleException {
        if (remotePointService.checkUserWasLogged(sessionId)) {
            //TODO:: реализовать восстановление сессии
            return;
        } else {
            remotePointService.loginUser(sessionId, message.getIMEI());
        }
    }
}

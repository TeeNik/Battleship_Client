package com.gridwar.mechanics.messages.login;

import com.gridwar.mechanics.UserQueueService;
import com.gridwar.websocket.HandleException;
import com.gridwar.websocket.MessageHandler;
import com.gridwar.websocket.MessageHandlerContainer;
import com.gridwar.websocket.SocketUserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class LoginHandler extends MessageHandler<LoginInput> {

    @NotNull
    private final UserQueueService userQueueService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final SocketUserService socketUserService;

    public LoginHandler(@NotNull UserQueueService userQueueService, @NotNull MessageHandlerContainer messageHandlerContainer, @NotNull SocketUserService socketUserService) {
        super(LoginInput.class);
        this.userQueueService = userQueueService;
        this.messageHandlerContainer = messageHandlerContainer;
        this.socketUserService = socketUserService;
    }

    @PostConstruct
    private void registerHandler() {
        messageHandlerContainer.registerHandler(LoginInput.class, this);
    }

    @Override
    public void handle(@NotNull LoginInput message, @NotNull String sessionId) throws HandleException {
        if (socketUserService.checkUserWasLogged(sessionId)) {
            //TODO:: реализовать восстановление сессии
            return;
        } else {
            socketUserService.loginUser(sessionId, message.getIMEI());

        }
    }
}

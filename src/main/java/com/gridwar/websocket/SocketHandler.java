package com.gridwar.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.constraints.NotNull;
import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;

@Component
public class SocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");

    private final @NotNull MessageHandlerContainer messageHandlerContainer;

    private final @NotNull SocketUserService socketUserService;

    private final ObjectMapper objectMapper;


    public SocketHandler(@NotNull MessageHandlerContainer messageHandlerContainer,
                         @NotNull SocketUserService socketUserService,
                         ObjectMapper objectMapper) {
        this.messageHandlerContainer = messageHandlerContainer;
        this.socketUserService = socketUserService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        socketUserService.registerUser(webSocketSession.getId(), webSocketSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        if (!webSocketSession.isOpen()) {
            return;
        }
        handleMessage(webSocketSession, message);
    }

    private void handleMessage(WebSocketSession webSocketSession, TextMessage text) {
        JSONObject jsonObject = new JSONObject(text.getPayload());
        String header = jsonObject.getString("header");
        try {
            messageHandlerContainer.handle(text, header, webSocketSession.getId()); //TODO:: возвращать ответ
        } catch (HandleException e) {
            LOGGER.error("Can't handle message of type " + header + " with content: " + text, e);
            //TODO:: возвращать Fault ответ
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        final String user = (String) webSocketSession.getAttributes().get("user");
        //TODO:  Переподключение
//        game.dropPlayer(game.getSessionForUser(user), user);
        if (user == null) {
            LOGGER.warn("User disconnected but his session was not found (closeStatus=" + closeStatus + ')');
            return;
        }
        socketUserService.removeUser(user);
    }

    private void closeSessionSilently(@NotNull WebSocketSession session, @Nullable CloseStatus closeStatus) {
        final CloseStatus status = closeStatus == null ? SERVER_ERROR : closeStatus;
        //noinspection OverlyBroadCatchBlock
        try {
            session.close(status);
        } catch (Exception ignore) {
        }

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

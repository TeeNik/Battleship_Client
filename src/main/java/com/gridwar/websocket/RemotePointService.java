package com.gridwar.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RemotePointService {
    private final Map<String, WebSocketSession> connectedUsers = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> loggedUsers = new ConcurrentHashMap<>();
    private Set<String> sessionIds;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void setSessionIds() {
        Map<String, Boolean> map = new ConcurrentHashMap<>();
        sessionIds = Collections.newSetFromMap(map);
    }

    public RemotePointService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerSession(String sessionId) {
        sessionIds.add(sessionId);
    }

    public void registerUser(@NotNull String sessionId, @NotNull WebSocketSession webSocketSession) {
        connectedUsers.put(sessionId, webSocketSession);
    }

    public boolean isConnected(@NotNull String user) {
        return connectedUsers.containsKey(user) && connectedUsers.get(user).isOpen();
    }

    public void removeUser(@NotNull String sessionId) {
        connectedUsers.remove(sessionId);
    }

    public void cutDownConnection(@NotNull String user, @NotNull CloseStatus closeStatus) {
        final WebSocketSession webSocketSession = connectedUsers.get(user);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close(closeStatus);
            } catch (IOException ignore) {
            }
        }
    }

    public void sendMessageToUser(@NotNull String user, @NotNull Message message) throws IOException {
        final WebSocketSession webSocketSession = connectedUsers.get(user);
        if (webSocketSession == null) {
            throw new IOException("no game websocket for user " + user);
        }
        if (!webSocketSession.isOpen()) {
            throw new IOException("session is closed or not exsists");
        }
        //noinspection OverlyBroadCatchBlock
        try {
            //noinspection ConstantConditions
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            throw new IOException("Unnable to send message", e);
        }
    }

    public boolean checkUserWasLogged(@NotNull String IMEI) {
        return loggedUsers.containsKey(IMEI);
    }

    public void loginUser(@NotNull String sessionId, @NotNull String IMEI) {
        loggedUsers.put(IMEI, connectedUsers.get(sessionId));
        connectedUsers.remove(sessionId);
    }
}
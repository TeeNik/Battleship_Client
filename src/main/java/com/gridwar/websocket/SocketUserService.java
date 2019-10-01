package com.gridwar.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridwar.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketUserService {

    private final Map<String, WebSocketSession> sessionID_Websocket = new ConcurrentHashMap<>();

    private final Map<String, User> sessionID_User = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    public SocketUserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerUser(@NotNull String sessionId, @NotNull WebSocketSession webSocketSession) {
        sessionID_Websocket.put(sessionId, webSocketSession);
    }

    public void loginUser(@NotNull String sessionId, @NotNull String IMEI) {
        WebSocketSession socketSession = sessionID_Websocket.get(sessionId);
        User user = new User(IMEI, socketSession);
        sessionID_User.put(sessionId, user);
    }

    public boolean checkUserWasLogged(@NotNull String sessionId) {
        return sessionID_User.containsKey(sessionId);
    }

    public void removeUser(@NotNull String sessionId) {
        sessionID_Websocket.remove(sessionId);
    }

    public User getUserBySessionId(String sessionId) {
        return sessionID_User.get(sessionId);
    }

    public void sendServerTime(User user) {
        WebSocketSession session = user.getSession();
        if (session.isOpen()) {
            //TODO
            sendMessageToUserBySessionId(session.getId(), new Message());
            //session.sendMessage(new TextMessage(LocalDateTime.now().toString()));
        }
    }

    public boolean isConnected(@NotNull String user) {
        return sessionID_Websocket.containsKey(user) && sessionID_Websocket.get(user).isOpen();
    }

    public void cutDownConnection(@NotNull String user, @NotNull CloseStatus closeStatus) {
        final WebSocketSession webSocketSession = sessionID_Websocket.get(user);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close(closeStatus);
            } catch (IOException ignore) {
            }
        }
    }

    public void sendMessageToUserBySessionId(@NotNull String sessionId, @NotNull Message message)  {
        final WebSocketSession webSocketSession = sessionID_Websocket.get(sessionId);
        if (webSocketSession == null) {
//            throw new IOException("no game websocket for user " + user);
        }
        if (!webSocketSession.isOpen()) {
//            throw new IOException("session is closed or not exsists");
        }
        //noinspection OverlyBroadCatchBlock
        try {
            //noinspection ConstantConditions
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
//            throw new IOException("Unnable to send message", e); //TODO продумать поведение
        }
    }

    public void sendMessageToUserBySession(WebSocketSession session, Message message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
//            e.printStackTrace(); //TODO продумать поведение
        }
    }
}
package com.gridwar.game;

import com.gridwar.game.message.GameMessage;
import com.gridwar.model.User;
import com.gridwar.websocket.Message;
import com.gridwar.websocket.SocketUserService;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Getter
@Setter
public class GameSession {

    private Map<String, BiConsumer<User, GameMessage>> gameCommands = new HashMap<>();
    private Map<String, GameMessage> userTurnMessages = new ConcurrentHashMap<>();
    //private final Message okMessage = new Message(0);

    public GameSession(SocketUserService messageService) {
        this.messageService = messageService;
        gameCommands.put("playerTurn", this::playerTurn);
    }

    private SocketUserService messageService;
    private User user1;
    private User user2;

    public void processMessage(User user, GameMessage message) {
        String cmd = message.getCmd();
        BiConsumer<User, GameMessage> handler = gameCommands.get(cmd);
        handler.accept(user, message);
    }

    private void playerTurn(User user, GameMessage message) {
        userTurnMessages.putIfAbsent(user.getIMEI(), message);
        if (userTurnMessages.size() == 2) {
            GameMessage gameMessage = new GameMessage();
            gameMessage.setCmd("nextTurn");
            gameMessage.setParams(userTurnMessages);
            gameMessage.setResult(0);
            messageService.sendMessageToUserBySession(user1.getSession(), gameMessage);
            messageService.sendMessageToUserBySession(user2.getSession(), gameMessage);
        } else {
            messageService.sendMessageToUserBySession(user.getSession(), message);
        }
    }
}

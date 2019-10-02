package com.gridwar.game;

import com.gridwar.game.message.PlayerTurnInput;
import com.gridwar.model.User;
import com.gridwar.websocket.Message;
import com.gridwar.websocket.ResponseMessage;
import com.gridwar.websocket.SocketUserService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Getter
@Setter
public class GameSession {

    private ArrayList<String> userTurnMessages = new ArrayList<>();

    public GameSession(SocketUserService messageService) {
        this.messageService = messageService;
    }

    private SocketUserService messageService;
    private User user1;
    private User user2;

    public void playerTurn(User user, PlayerTurnInput input) {
        userTurnMessages.add(input.getTurnData());
        if (userTurnMessages.size() == 2) {
            String bothTurns = userTurnMessages.get(0) + userTurnMessages.get(1);

            //TODO replace with output class
            PlayerTurnInput message = new PlayerTurnInput(bothTurns);
            message.setCmd("nextTurn");
            messageService.sendMessageToUserBySession(user1.getSession(), message);
            messageService.sendMessageToUserBySession(user2.getSession(), message);
        } else {
            messageService.sendMessageToUserBySession(user.getSession(), new ResponseMessage("playerTurn", 0));
        }
    }
}

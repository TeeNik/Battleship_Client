package com.gridwar.game;

import com.gridwar.game.message.PlayerTurn.PlayerTurnInput;
import com.gridwar.game.message.StartGame.NextTurnOutput;
import com.gridwar.model.User;
import com.gridwar.websocket.ResponseMessage;
import com.gridwar.websocket.SocketUserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

import java.util.ArrayList;

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
            //TODO replace with output class
            NextTurnOutput message = new NextTurnOutput(userTurnMessages);
            messageService.sendMessageToUserBySession(user1.getSession(), message);
            messageService.sendMessageToUserBySession(user2.getSession(), message);
        } else {
            messageService.sendMessageToUserBySession(user.getSession(), new ResponseMessage("playerTurn", 0));
        }
    }
}

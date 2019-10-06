package com.gridwar.game.message.StartGame;

import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;


public class StartGameOutput extends Message {

    public enum PlayerType{
        Player1,
        Player2
    }

    public StartGameOutput(PlayerType type){
        setCmd("startGame");
        setPlayerType(type);
    }

    @Getter @Setter
    private PlayerType playerType;

}

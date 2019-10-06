package com.gridwar.game.message.StartGame;

import com.gridwar.websocket.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class NextTurnOutput extends Message {

    @Getter @Setter
    private ArrayList<String> turnData;

    public NextTurnOutput(ArrayList<String> turnData){
        this.turnData = turnData;
        setCmd("nextTurn");
    }

}

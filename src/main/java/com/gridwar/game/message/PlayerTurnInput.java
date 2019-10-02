package com.gridwar.game.message;

import com.gridwar.websocket.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerTurnInput extends Message {

    @Getter @Setter
    private String turnData;

}

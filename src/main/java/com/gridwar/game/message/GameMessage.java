package com.gridwar.game.message;

public class GameMessage<T extends GameMessageParams> {
    private String cmd;
    private T params;
}

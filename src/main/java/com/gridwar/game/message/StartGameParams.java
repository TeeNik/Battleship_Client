package com.gridwar.game.message;

import lombok.Data;

import java.util.List;

@Data
public class StartGameParams extends GameMessageParams {
    private int width;
    private int height;
    private int [] map;
    private List<SpawnData> spawnDataList;
}

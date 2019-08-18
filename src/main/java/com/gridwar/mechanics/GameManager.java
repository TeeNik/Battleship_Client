package com.gridwar.mechanics;


import javax.validation.constraints.NotNull;

public interface GameManager {

    void addUserToQueue(@NotNull String sessionId);

}

package com.gridwar.mechanics;


import com.gridwar.game.GameSession;
import com.gridwar.model.User;

import javax.validation.constraints.NotNull;

public interface UserQueueService {

    void addUserToQueue(@NotNull String sessionId);

    GameSession getGameSessionByUser(User user);

}

package com.gridwar.mechanics;


import javax.validation.constraints.NotNull;

public interface UserQueueService {

    void addUserToQueue(@NotNull String sessionId);

}

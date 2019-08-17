package com.gridwar.mechanics;


import javax.validation.constraints.NotNull;

public interface GameMechanics {

    void addUser(@NotNull String user, @NotNull Integer players);

    void loginUser(@NotNull String IMEI);
}

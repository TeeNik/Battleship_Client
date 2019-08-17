package com.gridwar.mechanics;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class GameMechanicsIml implements GameMechanics {

    @Override
    public void addUser(@NotNull String user, @NotNull Integer players) {
        System.out.println("__________________add user triggered__________________");
    }

    @Override
    public void loginUser(@NotNull String IMEI) {
        System.out.println("_________________Леша пошла нахуй____________________");
        System.out.println(IMEI);
        System.out.println("_________________login user____________________");
    }
}

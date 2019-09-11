package com.gridwar.game;

import com.gridwar.model.User;
import com.gridwar.websocket.SocketUserService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSession {

    public GameSession() {
        // после создания игры
        // разослать игрокам startGame //TODO сделать удобный билдер
        // реализовать шину сообщений
        // public SystemController systemController
        // public EntityController entityManager
        // public MapController mapController;
    }

    private SocketUserService messageService;
    private User user1;
    private User user2;
}

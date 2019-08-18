package com.gridwar.mechanics;

import com.gridwar.model.User;
import com.gridwar.websocket.RemotePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class GameManagerIml implements GameManager {

    private final ConcurrentLinkedQueue<User> waitingUsers = new ConcurrentLinkedQueue<>();

    private final RemotePointService remotePointService;

    private Thread thread;

    @PostConstruct
    void launchItBaby() {
       new Thread(() -> {
            while (true) {
                waitingUsers
                        .parallelStream()
                        .forEach(remotePointService::sendServerTime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    @Override
    public void addUserToQueue(@NotNull String sessionId) {
        User user = remotePointService.getUserBySessionId(sessionId);
        waitingUsers.add(user);
    }

}

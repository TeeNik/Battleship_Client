package com.gridwar.mechanics;

import com.gridwar.game.GameSession;
import com.gridwar.model.User;
import com.gridwar.websocket.SocketUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class UserQueueServiceImpl implements UserQueueService {

    private final ConcurrentLinkedQueue<User> waitingUsers = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<User, GameSession> activeGames = new ConcurrentHashMap<>();
    private final SocketUserService socketUserService;
    private Thread serverTimeThread;
    private Thread gameSessionsThread;

    @PostConstruct
    void setServerTimeThread() {
       serverTimeThread = new  Thread(() -> {
            while (true) {
                waitingUsers
                        .parallelStream()
                        .forEach(socketUserService::sendServerTime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
       serverTimeThread.start();

       gameSessionsThread = new Thread(() -> {
          while (true) {
              if (waitingUsers.size() >= 2) {
                  User user1 = waitingUsers.poll();
                  User user2 = waitingUsers.poll();
                  GameSession gameSession = new GameSession(socketUserService);
                  gameSession.setUser1(user1);
                  gameSession.setUser2(user2);
                  activeGames.put(user1, gameSession);
                  activeGames.put(user2, gameSession);
              }
          }
       });
       gameSessionsThread.start();
    }

    @Override
    public void addUserToQueue(@NotNull String sessionId) {
        User user = socketUserService.getUserBySessionId(sessionId);
        waitingUsers.add(user);
    }

    @Override
    public GameSession getGamseSessionByUser(User user) {
        return activeGames.get(user);
    }

}

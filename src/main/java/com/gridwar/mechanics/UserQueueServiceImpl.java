package com.gridwar.mechanics;

import com.gridwar.game.GameSession;
import com.gridwar.game.message.StartGame.StartGameOutput;
import com.gridwar.model.User;
import com.gridwar.websocket.Message;
import com.gridwar.websocket.ResponseMessage;
import com.gridwar.websocket.SocketHandler;
import com.gridwar.websocket.SocketUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class UserQueueServiceImpl implements UserQueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserQueueServiceImpl.class);

    private final ConcurrentLinkedQueue<User> waitingUsers = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<User, GameSession> activeGames = new ConcurrentHashMap<>();
    private final SocketUserService socketUserService;
    private final int SyncDelay = 10000;
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
                    Thread.sleep(SyncDelay);
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
                  socketUserService.sendMessageToUserBySessionId(user1.getSession().getId(), new StartGameOutput(StartGameOutput.PlayerType.Player1));
                  socketUserService.sendMessageToUserBySessionId(user2.getSession().getId(), new StartGameOutput(StartGameOutput.PlayerType.Player2));
                  LOGGER.info(String.format("Started game session for: %s and %s", user1.getDeviceId(), user2.getDeviceId()));
              }
          }
       });
       gameSessionsThread.start();
    }

    @Override
    public void addUserToQueue(@NotNull String sessionId) {
        User user = socketUserService.getUserBySessionId(sessionId);
        waitingUsers.add(user);
        LOGGER.info(String.format("Added user %s to expecting queue", user.getDeviceId()));
    }

    @Override
    public GameSession getGameSessionByUser(User user) {
        return activeGames.get(user);
    }

}

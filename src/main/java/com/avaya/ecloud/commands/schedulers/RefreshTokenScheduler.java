package com.avaya.ecloud.commands.schedulers;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.executor.Executor;
import com.avaya.ecloud.model.command.CommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RefreshTokenScheduler {

    private Command loginCommand;
    private ResponseCache responseCache;
    private Executor executor;

    @Autowired
    public RefreshTokenScheduler(@Qualifier("loginCommand") Command loginCommand, ResponseCache responseCache, Executor executor) {
        this.loginCommand = loginCommand;
        this.responseCache = responseCache;
        this.executor = executor;
    }

    @Scheduled(fixedRate = 20000000)
    public void login() {
        Map<String, Map<String, Object>> refreshTokenData = responseCache.getRefreshTokenData();
        for (Map.Entry<String, Map<String, Object>> entry : refreshTokenData.entrySet()) {
            CommandData commandData = new CommandData("refreshToken", entry.getKey(), entry.getValue());
            executor.getExecutorService().execute(() -> loginCommand.execute(commandData));
        }
    }


}

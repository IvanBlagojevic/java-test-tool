package com.avaya.ecloud.commands.schedulers;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RefreshTokenScheduler {

    private Command loginCommand;
    private Cache cache;

    @Autowired
    public RefreshTokenScheduler(@Qualifier("loginCommand") Command loginCommand, Cache cache) {
        this.loginCommand = loginCommand;
        this.cache = cache;
    }

    @Scheduled(initialDelay = 20000000, fixedRate = 20000000)
    public void login() {
        Map<String, Map<String, Object>> refreshTokenData = cache.getRefreshTokenData();
        for (Map.Entry<String, Map<String, Object>> entry : refreshTokenData.entrySet()) {
            CommandData commandData = new CommandData("refreshToken", entry.getKey(), entry.getValue());
            new Thread(() -> loginCommand.execute(commandData)).start();
        }
    }



}

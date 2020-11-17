package com.avaya.ecloud.configuration;

import com.avaya.ecloud.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandsConfig {

    private Command loginCommand;
    private Command conferenceCommand;
    private Command sessionCommand;
    private Command resourceCommand;

    @Autowired
    public CommandsConfig(@Qualifier("loginCommand") Command loginCommand, @Qualifier("conferenceCommand") Command conferenceCommand, @Qualifier("sessionCommand") Command sessionCommand, @Qualifier("resourceCommand") Command resourceCommand) {
        this.loginCommand = loginCommand;
        this.conferenceCommand = conferenceCommand;
        this.sessionCommand = sessionCommand;
        this.resourceCommand = resourceCommand;
    }

    @Bean
    public Map<String, Command> commandsMap() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("createConference", conferenceCommand);
        commandMap.put("login", loginCommand);
        commandMap.put("createSession", sessionCommand);
        commandMap.put("discoverResources", resourceCommand);
        return commandMap;
    }
}

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
    private Command clientCommand;
    private Command activateServiceCommand;
    private Command createNewCallCommand;

    @Autowired
    public CommandsConfig(@Qualifier("loginCommand") Command loginCommand,
                          @Qualifier("conferenceCommand") Command conferenceCommand,
                          @Qualifier("sessionCommand") Command sessionCommand,
                          @Qualifier("resourceCommand") Command resourceCommand,
                          @Qualifier("clientCommand") Command clientCommand,
                          @Qualifier("activateServiceCommand") Command activateServiceCommand,
                          @Qualifier("createNewCallCommand") Command createNewCallCommand) {
        this.loginCommand = loginCommand;
        this.conferenceCommand = conferenceCommand;
        this.sessionCommand = sessionCommand;
        this.resourceCommand = resourceCommand;
        this.clientCommand = clientCommand;
        this.activateServiceCommand = activateServiceCommand;
        this.createNewCallCommand = createNewCallCommand;
    }

    @Bean
    public Map<String, Command> commandsMap() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("createConference", conferenceCommand);
        commandMap.put("login", loginCommand);
        commandMap.put("createSession", sessionCommand);
        commandMap.put("discoverResources", resourceCommand);
        commandMap.put("clientConnect", clientCommand);
        commandMap.put("activateCall", activateServiceCommand);
        commandMap.put("startAudioCall", createNewCallCommand);
        return commandMap;
    }
}

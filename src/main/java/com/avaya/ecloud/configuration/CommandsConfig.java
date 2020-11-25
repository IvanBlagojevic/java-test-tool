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

    Map<String, Command> commandMap;


    @Autowired
    public CommandsConfig(@Qualifier("loginCommand") Command loginCommand,
                          @Qualifier("conferenceCommand") Command conferenceCommand,
                          @Qualifier("sessionCommand") Command sessionCommand,
                          @Qualifier("resourceCommand") Command resourceCommand,
                          @Qualifier("connectWebSocketCommand") Command connectWebSocketCommand,
                          @Qualifier("activateServiceCommand") Command activateServiceCommand,
                          @Qualifier("createNewCallCommand") Command createNewCallCommand,
                          @Qualifier("eventSubscriptionCommand") Command eventsSubscriptionCommand,
                          @Qualifier("clientConnectSession") Command clientConnectSession,
                          @Qualifier("timeWaitCommand") Command timeWaitCommand) {
        setCommandMap(loginCommand,
                conferenceCommand,
                sessionCommand,
                resourceCommand,
                connectWebSocketCommand,
                activateServiceCommand,
                createNewCallCommand,
                eventsSubscriptionCommand,
                clientConnectSession,
                timeWaitCommand);

    }

    private void setCommandMap(Command loginCommand,
                               Command conferenceCommand,
                               Command sessionCommand,
                               Command resourceCommand,
                               Command connectWebSocketCommand,
                               Command activateServiceCommand,
                               Command createNewCallCommand,
                               Command eventsSubscriptionCommand,
                               Command clientConnectSession,
                               Command timeWaitCommand) {
        commandMap = new HashMap<>();
        commandMap.put("createConference", conferenceCommand);
        commandMap.put("login", loginCommand);
        commandMap.put("createSession", sessionCommand);
        commandMap.put("discoverResources", resourceCommand);
        commandMap.put("connectWebSocket", connectWebSocketCommand);
        commandMap.put("activateCall", activateServiceCommand);
        commandMap.put("startAudioCall", createNewCallCommand);
        commandMap.put("subscribeToEvents", eventsSubscriptionCommand);
        commandMap.put("clientConnectSession", clientConnectSession);
        commandMap.put("timeWait", timeWaitCommand);
    }

    @Bean
    public Map<String, Command> commandsMap() {
        return getCommandMap();
    }

    private Map<String, Command> getCommandMap() {
        return this.commandMap;
    }
}

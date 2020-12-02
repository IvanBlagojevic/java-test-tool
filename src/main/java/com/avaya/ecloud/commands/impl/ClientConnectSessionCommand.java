package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component("clientConnectSession")
public class ClientConnectSessionCommand extends BaseCommand implements Command {

    private List<Command> commands;

    @Autowired
    public ClientConnectSessionCommand(Cache cache,
                                       RestTemplate restTemplate,
                                       @Qualifier("resourceCommand") Command discoverResource,
                                       @Qualifier("connectWebSocketCommand") Command connectWebSocket,
                                       @Qualifier("eventSubscriptionCommand") Command eventSubscription,
                                       @Qualifier("activateServiceCommand") Command activateCall,
                                       @Qualifier("createNewCallCommand") Command startAudioCall) {
        super(cache, restTemplate);
        setCommands(discoverResource, connectWebSocket, eventSubscription, activateCall, startAudioCall);
    }

    @Override
    public void execute(CommandData commandData) {
        getCommands().forEach(command -> command.execute(commandData));
        executeNext(getUpdatedCommandData(commandData));
    }

    private CommandData getUpdatedCommandData(CommandData commandData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(commandData.getResponseData());
        return data;
    }


    private List<Command> getCommands() {
        return this.commands;
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }


    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    private void setCommands(Command discoverResource, Command connectWebSocket, Command eventSubscription, Command activateCall, Command startAudioCall) {
        commands = new ArrayList<>();
        commands.add(discoverResource);
        commands.add(connectWebSocket);
        commands.add(eventSubscription);
        commands.add(activateCall);
        commands.add(startAudioCall);
    }
}

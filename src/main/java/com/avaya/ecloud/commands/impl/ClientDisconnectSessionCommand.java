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

@Component("clientDisconnectSession")
public class ClientDisconnectSessionCommand extends BaseCommand implements Command {

    private List<Command> commands;

    @Autowired
    public ClientDisconnectSessionCommand(Cache cache,
                                          RestTemplate restTemplate,
                                          @Qualifier("eventsUnsubscribeCommand") Command eventsUnsubscribeCommand,
                                          @Qualifier("endEstablishedCallCommand") Command endEstablishedCallCommand,
                                          @Qualifier("deactivateServiceCommand") Command deactivateServiceCommand,
                                          @Qualifier("terminateClientCommand") Command terminateClientCommand,
                                          @Qualifier("deleteHttpSessionCommand") Command deleteHttpSessionCommand,
                                          @Qualifier("deleteSessionCommand") Command deleteSessionCommand) {
        super(cache, restTemplate);
        setCommands(eventsUnsubscribeCommand,
                endEstablishedCallCommand,
                deactivateServiceCommand,
                terminateClientCommand,
                deleteHttpSessionCommand,
                deleteSessionCommand);
    }

    @Override
    public void execute(CommandData commandData) {
        getCommands().forEach(command -> command.execute(commandData));
        executeNext(getUpdatedCommandData(commandData));
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }


    private List<Command> getCommands() {
        return this.commands;
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    private void setCommands(Command eventsUnsubscribeCommand,
                             Command endEstablishedCallCommand,
                             Command deactivateServiceCommand,
                             Command terminateClientCommand,
                             Command deleteHttpSessionCommand,
                             Command deleteSessionCommand) {

        commands = new ArrayList<>();
        commands.add(eventsUnsubscribeCommand);
        commands.add(endEstablishedCallCommand);
        commands.add(deactivateServiceCommand);
        commands.add(terminateClientCommand);
        commands.add(deleteHttpSessionCommand);
        commands.add(deleteSessionCommand);
    }
}

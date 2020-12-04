package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.aams.AamsConnection;
import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.network.ClientWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Component("connectWebSocketCommand")
public class ConnectWebSocketCommand extends BaseCommand implements Command {

    private AamsConnection connection;

    @Autowired
    public ConnectWebSocketCommand(Cache cache,
                                   @Qualifier("restTemplate") RestTemplate restTemplate,
                                   AamsConnection connection) {
        super(cache, restTemplate);
        this.connection = connection;
    }

    @Override
    public void execute(CommandData commandData) {

        getWebSocketConnectionManager(connection,
                commandData.getResponseData().getResourceData().getCallsUri(),
                commandData.getResponseData().getResourceData().getWebSocketUri()).start();

        logInfoOnFinish(commandData.getResponseData().getSessionId());
        executeNext(getUpdatedCommandData(commandData));
    }

    private WebSocketConnectionManager getWebSocketConnectionManager(AamsConnection connection, String callUri, String webSocketUri) {
        return new WebSocketConnectionManager(new StandardWebSocketClient(),
                new ClientWebSocketHandler(callUri, connection),
                webSocketUri);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    private void logInfoOnFinish(String sessionId) {
        LOGGER.info("Connect web socket FINISHED for session id " + sessionId);
    }
}

package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.aams.AamsConnection;
import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
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
    public ConnectWebSocketCommand(ScenarioCache scenarioCache,
                                   ResponseCache responseCache,
                                   @Qualifier("restTemplate") RestTemplate restTemplate,
                                   AamsConnection connection) {
        super(scenarioCache, responseCache, restTemplate);
        this.connection = connection;
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String webSocketUri = getResponseCache().getWebsocketUri(scenario);
        String callUri = getResponseCache().getCallsUri(scenario);

        WebSocketConnectionManager webSocketConnectionManager =
                new WebSocketConnectionManager(new StandardWebSocketClient(),
                        new ClientWebSocketHandler(callUri, connection),
                        webSocketUri);

        webSocketConnectionManager.start();

        logInfoOnFinish();
    }

    private void logInfoOnFinish() {
        LOGGER.info("Connect web socket FINISHED");
    }
}

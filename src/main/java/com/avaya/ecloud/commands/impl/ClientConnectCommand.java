package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Component("clientCommand")
public class ClientConnectCommand extends BaseCommand implements Command {

    private WebSocketClient webSocketClient;
    private WebSocketHandler webSocketHandler;

    @Autowired
    public ClientConnectCommand(ScenarioCache scenarioCache,
                                ResponseCache responseCache,
                                RestTemplate restTemplate,
                                @Qualifier("standardWebSocketClient") WebSocketClient webSocketClient,
                                @Qualifier("webSocketHandler") WebSocketHandler webSocketHandler) {
        super(scenarioCache, responseCache, restTemplate);

        this.webSocketClient = webSocketClient;
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String webSocketUri = getResponseCache().getWebsocketUri(scenario);

        WebSocketConnectionManager webSocketConnectionManager =
                new WebSocketConnectionManager(webSocketClient, webSocketHandler, webSocketUri);

        webSocketConnectionManager.start();

        logInfoOnFinish();
    }

    private void logInfoOnFinish() {
        LOGGER.info("Client connect FINISHED");
    }
}

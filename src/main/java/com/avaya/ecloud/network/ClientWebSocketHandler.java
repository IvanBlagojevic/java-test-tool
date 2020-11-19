package com.avaya.ecloud.network;

import com.avaya.ecloud.aams.AamsConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ClientWebSocketHandler extends TextWebSocketHandler {

    private AamsConnection connection;

    @Autowired
    public ClientWebSocketHandler(AamsConnection connection) {
        this.connection = connection;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("afterConnectionEstablishedD");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOGGER.info("afterConnectionClosed");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        switch (getEventTypeFromPayload(message.getPayload())) {
            case "notification":
                LOGGER.info("NOTIFICATION");
                break;
            case "discovery":
                LOGGER.info("DISCOVERY");
                break;
            case "subscribed":
                LOGGER.info("SUBSCRIBED");
                break;
            case "notified":
                LOGGER.info("NOTIFIED");
                break;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        LOGGER.info("handleTransportError");
    }

    private String getEventTypeFromPayload(String payload) {
        String substring = payload.substring(payload.indexOf("{") + 1, payload.indexOf(":")).trim();
        return substring.substring(1, substring.length() - 1);
    }
}

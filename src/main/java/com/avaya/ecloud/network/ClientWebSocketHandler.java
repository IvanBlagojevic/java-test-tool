package com.avaya.ecloud.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class ClientWebSocketHandler extends TextWebSocketHandler {

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
        LOGGER.info("handleTextMessage");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        LOGGER.info("handleTransportError");
    }
}

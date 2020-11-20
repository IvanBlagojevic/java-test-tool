package com.avaya.ecloud.network;

import com.avaya.ecloud.aams.AamsConnection;
import com.avaya.ecloud.model.events.NotificationEvent;
import com.avaya.ecloud.utils.ModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class ClientWebSocketHandler extends TextWebSocketHandler {

    private String callUri;
    private AamsConnection connection;

    public ClientWebSocketHandler(String callUri, AamsConnection connection) {
        this.callUri = callUri;
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

    private String getDiscoveryMessage(String payload) {
        String subscribe = ModelUtil.getJsonFromFile("wssSubscribe.json");
        return StringUtils.replace(subscribe, "${CALLS_URI}", callUri);
    }

    private String getNotificationMessage(String payload) {
        NotificationEvent event = ModelUtil.getNotificationEventFromPayload(payload);

        return "";
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        String eventType = getEventTypeFromPayload(payload);

        switch (eventType) {
            case "notification":
//                session.sendMessage(new TextMessage(getNotificationMessage(payload).getBytes()));
                LOGGER.info("NOTIFICATION_EVENT");
                break;
            case "discovery":
                LOGGER.info("DISCOVERY_EVENT");
                session.sendMessage(new TextMessage(getDiscoveryMessage(payload).getBytes()));
                break;
            case "subscribed":
                LOGGER.info("SUBSCRIBED_EVENT");
                break;
            case "notified":
                LOGGER.info("NOTIFIED_EVENT");
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

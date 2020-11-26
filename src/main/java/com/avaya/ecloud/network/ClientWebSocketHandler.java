package com.avaya.ecloud.network;

import com.avaya.ecloud.aams.AamsConnection;
import com.avaya.ecloud.model.aams.SessionInfo;
import com.avaya.ecloud.model.events.NotificationEvent;
import com.avaya.ecloud.model.sdp.SdpAnswer;
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
    private AamsConnection auraServerConnection;
    private SessionInfo sessionInfo;

    public ClientWebSocketHandler(String callUri, AamsConnection auraServerConnection) {
        this.callUri = callUri;
        this.auraServerConnection = auraServerConnection;

        // TODO We should configure AAMS connection only when needed
        auraServerConnection.createControlContext();
        sessionInfo = auraServerConnection.createSession();
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

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        String eventType = getEventTypeFromPayload(payload);
        switch (eventType) {
            case "notification":
                LOGGER.info("NOTIFICATION_EVENT: " + payload);
                processNotificationEvents(session, payload);
                break;
            case "discovery":
                LOGGER.info("DISCOVERY_EVENT");
                session.sendMessage(new TextMessage(getDiscoveryMessage(payload).getBytes()));
                break;
            case "subscribed":
                LOGGER.info("SUBSCRIBED_EVENT");
                break;
            case "notified":
                LOGGER.info("NOTIFIED_EVENT:" + payload);
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

    private void processNotificationEvents(WebSocketSession session, String payload) throws IOException {
        NotificationEvent notificationEvent = getNotificationEventFromPayload(payload);
        String messageType = notificationEvent.getNotification().getContents().getMessageType();
        LOGGER.info("MESSAGE_TYPE: " + messageType);
        switch (messageType) {
            case "createMediaRequest":
                sendMessageForMediaRequest(session, notificationEvent);
                sendMessageForProcessMedia(session, notificationEvent);
                break;
            case "processMediaRequest":
                sendSpdAnswer(payload);
                break;
            case "callEstablished":
                LOGGER.info("NOTIFICATION_CALL_ESTABLISHED");
                break;
            case "serviceStatusChanged":
                deleteSession(session, notificationEvent);
                break;
            default:
                break;
        }
    }

    private String generateMediaResponseMessage(String newSdpOffer, String callId) {
        // TODO We should switch from hardcoded value to dinamically configure
        return ModelUtil.getJsonFromFile("createMediaResponseWithCustomSdp.json")
                .replace("${CALL_ID}", callId)
                .replace("${CALLS_URI}", callUri).replace("${SDP}", newSdpOffer);
    }

    private void sendMessageForMediaRequest(WebSocketSession session,
                                            NotificationEvent notificationEvent) throws IOException {
        String mediaResponseMessage = generateMediaResponseMessage(sessionInfo.getSdpOffer(),
                notificationEvent.getNotification().getContents().getCallId());
        session.sendMessage(new TextMessage(mediaResponseMessage));
    }

    private void sendMessageForProcessMedia(WebSocketSession session,
                                            NotificationEvent notificationEvent) throws IOException {
        String processMediaResponse = ModelUtil.getJsonFromFile("processMediaResponse.json")
                .replace("${CALL_ID}", notificationEvent.getNotification().getContents().getCallId())
                .replace("${CALLS_URI}", callUri);
        session.sendMessage(new TextMessage(processMediaResponse));
    }

    private void sendSpdAnswer(String payload) {
        NotificationEvent notification = ModelUtil.getNotificationEventFromPayload(payload);
        SdpAnswer sdpAnswer = ModelUtil.getSdpAnswerFromMessageData(notification.getNotification().getContents().getMessageData());
        auraServerConnection.updateSession(sessionInfo.getSid(), sdpAnswer.getActionDetails().getSdp());
    }

    private void deleteSession(WebSocketSession session, NotificationEvent event) {
        if (isServiceDeactivated(event)) {
            if (session.isOpen()) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            auraServerConnection.deleteSession(sessionInfo.getSid());
        }
    }

    private NotificationEvent getNotificationEventFromPayload(String payload) {
        return ModelUtil.getNotificationEventFromPayload(payload);
    }

    private boolean isServiceDeactivated(NotificationEvent notificationEvent) {
        // TODO We should refactor hardcoded strings
        return notificationEvent.getNotification().getContents().getStatus().equals("deactivated");
    }
}

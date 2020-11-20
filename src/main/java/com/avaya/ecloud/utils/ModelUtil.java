package com.avaya.ecloud.utils;

import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.events.DiscoveryEvent;
import com.avaya.ecloud.model.events.NotificationEvent;
import com.avaya.ecloud.model.requests.SubscriptionRequest;
import com.avaya.ecloud.model.requests.activateService.ActivateService;
import com.avaya.ecloud.model.requests.conference.CreateConferenceRequest;
import com.avaya.ecloud.model.requests.session.CreateSessionRequest;
import com.avaya.ecloud.model.requests.startAudioCall.AudioCall;
import com.avaya.ecloud.model.response.session.SessionResponse;
import com.avaya.ecloud.model.response.session.SessionToken;
import com.avaya.ecloud.model.response.startAudioCall.StartAudioCallResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ModelUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtil.class);

    public static String getCallIdFromResponse(String response) {
        try {
            StartAudioCallResponse startAudioCallResponse = OBJECT_MAPPER.readValue(response, StartAudioCallResponse.class);
            return startAudioCallResponse.getCallId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static CreateConferenceRequest getCreateConferenceRequestFromFile(String fileName) {
        try {
            String jsonString = getJsonFromFile(fileName);
            return OBJECT_MAPPER.readValue(jsonString, CreateConferenceRequest.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static DiscoveryEvent getDiscoveryEventFromPayload(String payload) {
        try {
            return OBJECT_MAPPER.readValue(payload, DiscoveryEvent.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static NotificationEvent getNotificationEventFromPayload(String payload) {
        try {
            return OBJECT_MAPPER.readValue(payload, NotificationEvent.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static AudioCall getAudioCallRequestFromFile(String fileName) {
        try {
            InputStream is = CreateConferenceRequest.class.getClassLoader().getResourceAsStream(fileName);
            return OBJECT_MAPPER.readValue("{    \"sessionId\":\"dd3b86e7e03b424a2c62683a3d09f12127ef54c3\",    \"subject\":\"demo call\",    \"priority\":null,    \"privacy\":null,    \"participants\":    [{        \"participantId\":null,        \"remoteDisplayName\":\"\",        \"remoteAddress\":\"11111\"    }],    \"conferenceData\":null,    \"audioChannel\":{        \"channelId\":1,        \"state\":\"ENABLE\",        \"direction\":\"SEND_RECEIVE\"    },    \"videoChannels\":[],    \"desiredBandwidth\":null}", AudioCall.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String getJsonFromFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/" + fileName)));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static SubscriptionRequest getSubscriptionRequestFromFile(String fileName) {
        try {
            String jsonString = getJsonFromFile(fileName);
            return OBJECT_MAPPER.readValue(jsonString, SubscriptionRequest.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static CreateSessionRequest getCreateSessionRequestFromFile(String fileName) {
        try {
            String jsonString = getJsonFromFile(fileName);
            return OBJECT_MAPPER.readValue(jsonString, CreateSessionRequest.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ActivateService getActivateServiceRequestFromFile(String fileName) {
        try {
            String jsonString = getJsonFromFile(fileName);
            return OBJECT_MAPPER.readValue(jsonString, ActivateService.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpEntity<String> getEntityFromObject(Object request, HttpHeaders header) {
        String body = null;
        if (!Objects.isNull(request)) {
            try {
                body = OBJECT_MAPPER.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return new HttpEntity<>(body, header);
    }

    public static HttpHeaders getRequestHeader(String authToken, HttpHeaderEnum headerEnum) {
        HttpHeaders header = new HttpHeaders();

        String accept = headerEnum.getAccept();
        String contentType = headerEnum.getContentType();

        if (!StringUtils.isEmpty(authToken)) {
            header.set("Authorization", "Bearer " + authToken);
        }

        if (!StringUtils.isEmpty(accept)) {
            header.set("Accept", accept);
        }

        if (!StringUtils.isEmpty(contentType)) {
            header.set("Content-Type", contentType);
        }

        return header;
    }

    public static SessionToken getSessionTokenFromResponse(SessionResponse response) {
        try {
            return OBJECT_MAPPER.readValue(new String(Base64.decodeBase64(response.getSessionToken())), SessionToken.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}

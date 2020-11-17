package com.avaya.ecloud.commands.utils;

import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.conference.CreateConferenceRequest;
import com.avaya.ecloud.model.requests.session.CreateSessionRequest;
import com.avaya.ecloud.model.response.session.SessionResponse;
import com.avaya.ecloud.model.response.session.SessionToken;
import com.avaya.ecloud.model.requests.LoginRequest;
import com.avaya.ecloud.model.requests.SubscriptionRequest;
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
import java.util.Objects;

public class CommandUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandUtil.class);


    public static CreateConferenceRequest getCreateConferenceRequestFromFile(String fileName) {
        try {
            InputStream is = CreateConferenceRequest.class.getClassLoader().getResourceAsStream(fileName);
            return OBJECT_MAPPER.readValue(is, CreateConferenceRequest.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static LoginRequest getLoginRequestFromFile(String fileName) {
        try {
            InputStream is = CreateConferenceRequest.class.getClassLoader().getResourceAsStream(fileName);
            return OBJECT_MAPPER.readValue(is, LoginRequest.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static SubscriptionRequest getSubscriptionRequestFromFile(String fileName) {
        try {
            InputStream is = CreateConferenceRequest.class.getClassLoader().getResourceAsStream(fileName);
            return OBJECT_MAPPER.readValue(is, SubscriptionRequest.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static CreateSessionRequest getCreateSessionRequestFromFile(String fileName) {
        try {
            InputStream is = CreateConferenceRequest.class.getClassLoader().getResourceAsStream(fileName);
            return OBJECT_MAPPER.readValue(is, CreateSessionRequest.class);
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

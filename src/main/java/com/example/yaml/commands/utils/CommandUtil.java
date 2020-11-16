package com.example.yaml.commands.utils;

import com.example.yaml.model.enums.HttpHeaderEnum;
import com.example.yaml.model.requests.CreateConferenceRequest;
import com.example.yaml.model.requests.CreateSessionRequest;
import com.example.yaml.model.requests.LoginRequest;
import com.example.yaml.model.requests.SubscriptionRequest;
import com.example.yaml.model.response.SessionResponse;
import com.example.yaml.model.response.SessionToken;
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
        try {
            return new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(request), header);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpHeaders getRequestHeader(String authToken, HttpHeaderEnum headerEnum) {
        HttpHeaders header = new HttpHeaders();

        if (!StringUtils.isEmpty(authToken)) {
            header.set("Authorization", "Bearer " + authToken);
        }

        header.set("Content-Type", headerEnum.getContentType());
        header.set("Accept", headerEnum.getAccept());
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

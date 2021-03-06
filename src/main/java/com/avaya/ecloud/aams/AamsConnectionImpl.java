package com.avaya.ecloud.aams;

import com.avaya.ecloud.model.aams.SessionInfo;
import com.avaya.ecloud.model.enums.AamsEntityEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AamsConnectionImpl implements AamsConnection {

    private RestTemplate restTemplate;
    private static final String CONTROL_CONTEXT = "java-tests";
    private static final Logger LOGGER = LoggerFactory.getLogger(AamsConnectionImpl.class);

    @Autowired
    public AamsConnectionImpl(@Qualifier("restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void createControlContext() {
        String url = getBaseUrl() + "/mediacontrol/" + CONTROL_CONTEXT;
        HttpEntity<String> entity = new HttpEntity<>(AamsEntityEnum.CONTROL_CONTEXT.getEntity(), getHttpHeader());
        execute(url, HttpMethod.PUT, entity);
    }

    private String execute(String url, HttpMethod method, HttpEntity<String> entity) {

        String response = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, entity, String.class);
            response = responseEntity.getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return response;
    }

    @Override
    public SessionInfo createSession() {
        String url = getBaseUrl() + "/mediacontrol/" + CONTROL_CONTEXT + "/sessions";
        HttpEntity<String> entity = new HttpEntity<>(AamsEntityEnum.CREATE_SESSION.getEntity(), getHttpHeader());
        String response = execute(url, HttpMethod.POST, entity);
        return new SessionInfo(parseSidFromResponse(response), parseSdpOfferFromResponse(response));
    }

    @Override
    public void updateSession(String sessionId, String sdpAnswer) {
        String url = getBaseUrl() + "/mediacontrol/" + CONTROL_CONTEXT + "/sessions/" + sessionId;
        HttpEntity<String> entity = new HttpEntity<>(String.format(AamsEntityEnum.UPDATE_SESSION.getEntity(), sdpAnswer), getHttpHeader());
        execute(url, HttpMethod.POST, entity);
    }

    @Override
    public void deleteSession(String sessionId) {
        String url = getBaseUrl() + "/mediacontrol/" + CONTROL_CONTEXT + "/sessions/" + sessionId;
        execute(url, HttpMethod.DELETE, null);
    }

    @Override
    public void injectAudioViaVxml(String sessionId) {
        String url = getBaseUrl() + "/mediacontrol/" + CONTROL_CONTEXT + "/sessions/" + sessionId;
        HttpEntity<String> entity = new HttpEntity<>(String.format(AamsEntityEnum.INJECT_AUDIO_XML.getEntity(), sessionId), getHttpHeader());
        execute(url, HttpMethod.POST, entity);
    }

    // NOTE THAT THIS REQUIRES AN EXTERNAL MRCPv1 or MRCPv2 RESOURCE (+CONFIG) ON AMS
    @Override
    public void injectAudioViaTts(String sessionId) {
        String url = getBaseUrl() + "/mediacontrol/" + CONTROL_CONTEXT + "/sessions/" + sessionId;
        HttpEntity<String> entity = new HttpEntity<>(String.format(AamsEntityEnum.INJECT_AUDIO_TTS.getEntity(), sessionId, sessionId), getHttpHeader());
        execute(url, HttpMethod.POST, entity);
    }

    private String getBaseUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append("https://").append(System.getProperty("aamsIP", "34.70.241.25")).append(":").append(System.getProperty("aamsPort", "7151"));
        return builder.toString();
    }

    private String parseSidFromResponse(String response) {
        return StringUtils.substringBetween(response, "sid=\"", "\"");
    }

    private String parseSdpOfferFromResponse(String response) {
        return StringUtils.substringBetween(response, "<sdpOffer>", "</sdpOffer>");
    }

    private HttpHeaders getHttpHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_XML);
        return header;
    }

}

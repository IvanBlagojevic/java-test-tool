package com.avaya.ecloud.cache.impl;

import com.avaya.ecloud.model.cache.ResponseDetails;
import com.avaya.ecloud.cache.ResponseCache;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseCacheImpl implements ResponseCache {

    private final Map<String, ResponseDetails> RESPONSE_CACHE = new ConcurrentHashMap<>();

    private ResponseDetails getResponseDetail(String scenarioName) {
        return RESPONSE_CACHE.get(scenarioName);
    }

    @Override
    public void put(String scenarioName, ResponseDetails details) {
        RESPONSE_CACHE.put(scenarioName, details);
    }

    @Override
    public String getAccountId(String scenarioName) {
        return getResponseDetail(scenarioName).getAccountId();
    }

    @Override
    public String getAccountSecret(String scenarioName) {
        return getResponseDetail(scenarioName).getAccountSecret();
    }

    @Override
    public List<String> getConferenceIds(String scenarioName) {
        return getResponseDetail(scenarioName).getConferenceIds();
    }

    @Override
    public List<String> getSessionIds(String scenarioName) {
        return getResponseDetail(scenarioName).getSessionIds();
    }

    @Override
    public String getAuthToken(String scenarioName) {
        return getResponseDetail(scenarioName).getAuthToken();
    }

    @Override
    public void saveAuthToken(String scenarioName, String token) {
        getResponseDetail(scenarioName).setAuthToken(token);
    }

    @Override
    public void saveConferenceId(String scenarioName, String id) {
        getResponseDetail(scenarioName).getConferenceIds().add(id);
    }

    @Override
    public void saveSessionId(String scenarioName, String id) {
        getResponseDetail(scenarioName).getSessionIds().add(id);
    }

    @Override
    public void saveSubscriptionId(String scenarioName, String id) {
        getResponseDetail(scenarioName).setSubscriptionId(id);
    }

    @Override
    public String getSubscriptionId(String scenarioName) {
        return getResponseDetail(scenarioName).getSubscriptionId();
    }

    @Override
    public Map<String, Map<String, Object>> getRefreshTokenData() {
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Map.Entry<String, ResponseDetails> entry : RESPONSE_CACHE.entrySet()) {
            Map<String, Object> loginDetails = new HashMap<>();
            loginDetails.put("accountId", entry.getValue().getAccountId());
            loginDetails.put("accountSecret", entry.getValue().getAccountSecret());
            map.put(entry.getKey(), loginDetails);
        }
        return map;
    }

    @Override
    public String getSessionToken(String scenarioName) {
        return getResponseDetail(scenarioName).getSessionToken();
    }

    @Override
    public void saveSessionToken(String scenarioName, String token) {
        getResponseDetail(scenarioName).setSessionToken(token);
    }

    @Override
    public String getUserAgentURL(String scenarioName) {
        return getResponseDetail(scenarioName).getUserAgentURL();
    }

    @Override
    public void saveUserAgentURL(String scenarioName, String url) {
        getResponseDetail(scenarioName).setUserAgentURL(url);
    }

    @Override
    public void saveWebsocketUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setWebsocketUri(uri);
    }

    @Override
    public void saveSseUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setSseUri(uri);
    }

    @Override
    public void saveTerminateClientUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setTerminateClientUri(uri);
    }

    @Override
    public void saveDeleteSessionUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setDeleteSessionUri(uri);
    }

    @Override
    public String getWebsocketUri(String scenarioName) {
        return getResponseDetail(scenarioName).getWebsocketUri();
    }

    @Override
    public String getSseUri(String scenarioName) {
        return getResponseDetail(scenarioName).getSseUri();
    }

    @Override
    public String getTerminateClientUri(String scenarioName) {
        return getResponseDetail(scenarioName).getTerminateClientUri();
    }

    @Override
    public String getDeleteSessionUri(String scenarioName) {
        return getResponseDetail(scenarioName).getDeleteSessionUri();
    }

    @Override
    public void saveCallsUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setCallsUri(uri);
    }

    @Override
    public void saveEventsUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setEventsUri(uri);
    }

    @Override
    public void saveServicesUri(String scenarioName, String uri) {
        getResponseDetail(scenarioName).setServicesUri(uri);
    }

    @Override
    public String getCallsUri(String scenarioName) {
        return getResponseDetail(scenarioName).getCallsUri();
    }

    @Override
    public String getEventsUri(String scenarioName) {
        return getResponseDetail(scenarioName).getEventsUri();
    }

    @Override
    public String getServicesUri(String scenarioName) {
        return getResponseDetail(scenarioName).getServicesUri();
    }
}

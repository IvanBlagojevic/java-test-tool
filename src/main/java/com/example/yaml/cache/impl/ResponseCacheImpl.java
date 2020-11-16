package com.example.yaml.cache.impl;

import com.example.yaml.cache.ResponseCache;
import com.example.yaml.model.cache.ResponseDetails;
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
}

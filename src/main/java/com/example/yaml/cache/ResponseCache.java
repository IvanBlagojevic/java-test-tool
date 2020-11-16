package com.example.yaml.cache;

import com.example.yaml.model.cache.ResponseDetails;

import java.util.List;
import java.util.Map;

public interface ResponseCache {

    void put(String scenarioName, ResponseDetails details);

    String getAccountId(String scenarioName);

    String getAccountSecret(String scenarioName);

    List<String> getConferenceIds(String scenarioName);

    List<String> getSessionIds(String scenarioName);

    String getAuthToken(String scenarioName);

    void saveAuthToken(String scenarioName, String token);

    void saveConferenceId(String scenarioName, String id);

    void saveSessionId(String scenarioName, String id);

    void saveSubscriptionId(String scenarioName, String id);

    String getSubscriptionId(String scenarioName);

    Map<String, Map<String, Object>> getRefreshTokenData();

    String getSessionToken(String scenarioName);

    void saveSessionToken(String scenarioName, String token);

    String getUserAgentURL(String scenarioName);

    void saveUserAgentURL(String scenarioName, String url);
}


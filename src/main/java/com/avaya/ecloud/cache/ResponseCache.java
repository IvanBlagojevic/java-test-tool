package com.avaya.ecloud.cache;

import com.avaya.ecloud.model.cache.ResponseDetails;

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

    void saveWebsocketUri(String scenarioName, String uri);

    void saveSseUri(String scenarioName, String uri);

    void saveTerminateClientUri(String scenarioName, String uri);

    void saveDeleteSessionUri(String scenarioName, String uri);

    String getWebsocketUri(String scenarioName);

    String getSseUri(String scenarioName);

    String getTerminateClientUri(String scenarioName);

    String getDeleteSessionUri(String scenarioName);

    void saveCallsUri(String scenarioName, String uri);

    void saveEventsUri(String scenarioName, String uri);

    void saveServicesUri(String scenarioName, String uri);

    String getCallsUri(String scenarioName);

    String getEventsUri(String scenarioName);

    String getServicesUri(String scenarioName);

    void saveCallId(String scenarioName, String callId);

    String getCallId(String scenarioName);
}


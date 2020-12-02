package com.avaya.ecloud.cache;

import com.avaya.ecloud.model.cache.ScenarioDetails;

import java.util.List;
import java.util.Map;

public interface Cache {

    void put(String scenarioName, ScenarioDetails details);

    String getAccountId(String scenarioName);

    String getBaseUrl(String scenarioName);

    int getConferenceCounter(String scenarioName);

    int getSessionCounter(String scenarioName);

    int getRampTime(String scenarioName);

    int getNumberOfIterations(String scenarioName);

    String getAccountSecret(String scenarioName);

    void save(String scenarioName, ScenarioDetails details);

    String getSubscriptionUrl(String scenarioName);

    void saveSubscriptionId(String id);

    String getSubscriptionId(String scenarioName);

    void saveAuthToken(String auth);

    String getAuthToken(String scenarioName);

    void saveConferenceId(List<String> ids);

    List<String> getConferenceIds(String scenarioName);

    String getAccountId();

    String getAccountSecret();

    Map<String, Map<String, Object>> getRefreshTokenData();




}

package com.example.yaml.cache;

import com.example.yaml.model.cache.ScenarioDetails;

public interface ScenarioCache {

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

}

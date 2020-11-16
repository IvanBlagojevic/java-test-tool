package com.example.yaml.cache.impl;

import com.example.yaml.cache.ScenarioCache;
import com.example.yaml.model.cache.ScenarioDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScenarioCacheImpl implements ScenarioCache {

    private final Map<String, ScenarioDetails> SCENARIO_CACHE = new ConcurrentHashMap<>();

    @Override
    public void put(String scenarioName, ScenarioDetails details) {
        SCENARIO_CACHE.put(scenarioName, details);
    }

    @Override
    public String getAccountId(String scenarioName) {

        return getScenarioDetails(scenarioName).getAccountId();
    }

    @Override
    public String getBaseUrl(String scenarioName) {
        return getScenarioDetails(scenarioName).getBaseUrl();
    }

    @Override
    public int getConferenceCounter(String scenarioName) {
        return getScenarioDetails(scenarioName).getConferencesCounter();
    }

    @Override
    public int getSessionCounter(String scenarioName) {
        return getScenarioDetails(scenarioName).getSessionCounter();
    }

    @Override
    public int getRampTime(String scenarioName) {
        return getScenarioDetails(scenarioName).getRampTime();
    }

    @Override
    public int getNumberOfIterations(String scenarioName) {
        return getScenarioDetails(scenarioName).getNumberOfIterations();
    }

    @Override
    public String getAccountSecret(String scenarioName) {
        return getScenarioDetails(scenarioName).getAccountSecret();
    }

    @Override
    public void save(String scenarioName, ScenarioDetails details) {
        SCENARIO_CACHE.put(scenarioName, details);
    }

    @Override
    public String getSubscriptionUrl(String scenarioName) {
        return getScenarioDetails(scenarioName).getCallbackURL();
    }

    private ScenarioDetails getScenarioDetails(String scenarioName) {
        return SCENARIO_CACHE.get(scenarioName);
    }
}

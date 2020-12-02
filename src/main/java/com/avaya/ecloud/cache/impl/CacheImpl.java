package com.avaya.ecloud.cache.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.model.cache.ScenarioDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheImpl implements Cache {

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

    @Override
    public void saveSubscriptionId(String id) {
        for (String scenario : getScenarios()) {
            getScenarioDetails(scenario).setSubscriptionId(id);
        }
    }

    @Override
    public String getSubscriptionId(String scenarioName) {
        return getScenarioDetails(scenarioName).getSubscriptionId();
    }

    @Override
    public void saveAuthToken(String auth) {
        for (String scenario : getScenarios()) {
            getScenarioDetails(scenario).setAuthToken(auth);
        }

    }

    @Override
    public String getAuthToken(String scenarioName) {
        return getScenarioDetails(scenarioName).getAuthToken();
    }

    @Override
    public void saveConferenceId(List<String> ids) {
        for (String scenario : getScenarios()) {
            getScenarioDetails(scenario).setConferenceIds(ids);
        }
    }

    @Override
    public List<String> getConferenceIds(String scenarioName) {
        return getScenarioDetails(scenarioName).getConferenceIds();
    }

    @Override
    public String getAccountId() {
        Optional<String> first = getScenarios().stream().findFirst();
        return first.map(s -> getScenarioDetails(s).getAccountId()).orElse(null);
    }

    @Override
    public String getAccountSecret() {
        Optional<String> first = getScenarios().stream().findFirst();
        return first.map(s -> getScenarioDetails(s).getAccountSecret()).orElse(null);
    }

    @Override
    public Map<String, Map<String, Object>> getRefreshTokenData() {
        Optional<String> first = getScenarios().stream().findFirst();
        Map<String, Map<String, Object>> map = new HashMap<>();
        if (first.isPresent()){
            Map<String, Object> loginDetails = new HashMap<>();
            loginDetails.put("accountId", getScenarioDetails(first.get()).getAccountId());
            loginDetails.put("accountSecret", getScenarioDetails(first.get()).getAccountSecret());
            map.put(first.get(), loginDetails);
        }
        return map;
    }

    private Collection<String> getScenarios() {
        return SCENARIO_CACHE.keySet();
    }

    private ScenarioDetails getScenarioDetails(String scenarioName) {
        return SCENARIO_CACHE.get(scenarioName);
    }
}

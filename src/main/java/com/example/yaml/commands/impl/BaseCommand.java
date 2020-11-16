package com.example.yaml.commands.impl;

import com.example.yaml.cache.ResponseCache;
import com.example.yaml.cache.ScenarioCache;
import org.springframework.web.client.RestTemplate;


public class BaseCommand {

    private ScenarioCache scenarioCache;

    private ResponseCache responseCache;

    private RestTemplate restTemplate;

    public ScenarioCache getScenarioCache() {
        return scenarioCache;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public ResponseCache getResponseCache() {
        return responseCache;
    }

    public BaseCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        this.scenarioCache = scenarioCache;
        this.responseCache = responseCache;
        this.restTemplate = restTemplate;
    }
}

package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import org.springframework.web.client.RestTemplate;


abstract class BaseCommand {

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

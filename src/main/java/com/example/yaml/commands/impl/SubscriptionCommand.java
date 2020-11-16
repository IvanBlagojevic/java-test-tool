package com.example.yaml.commands.impl;

import com.example.yaml.cache.ResponseCache;
import com.example.yaml.cache.ScenarioCache;
import com.example.yaml.commands.Command;
import com.example.yaml.commands.utils.CommandUtil;
import com.example.yaml.model.command.CommandData;
import com.example.yaml.model.enums.ApiUrlEnum;
import com.example.yaml.model.enums.HttpHeaderEnum;
import com.example.yaml.model.requests.SubscriptionRequest;
import com.example.yaml.model.response.SubscriptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("subscriptionCommand")
public class SubscriptionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCommand.class);

    @Autowired
    public SubscriptionCommand(RestTemplate restTemplate, ScenarioCache scenarioCache, ResponseCache responseCache) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String accountId = (String) commandData.getConfig().get("accountId");

        SubscriptionRequest request = CommandUtil.getSubscriptionRequestFromFile((String) commandData.getConfig().get("config"));
        request.setCallbackURL(getScenarioCache().getSubscriptionUrl(scenario));

        HttpEntity<String> entity = CommandUtil.getEntityFromObject(request, CommandUtil.getRequestHeader(getResponseCache().getAuthToken(scenario), HttpHeaderEnum.CREATE_SUBSCRIPTION));

        try {
            logInfoOnStart(accountId);
            SubscriptionResponse response = getRestTemplate().postForObject(getSubscriptionUrl(scenario), entity, SubscriptionResponse.class);
            getResponseCache().saveSubscriptionId(scenario, response.getId());
            logInfoOnFinish(accountId, response.getId());
        } catch (Exception e) {
            logError(accountId, e);
        }
    }

    private void logInfoOnStart(String accountId) {
        LOGGER.info("Creating subscription STARTED for account id: " + accountId);
    }

    private void logError(String accountId, Exception e) {
        LOGGER.error("Creating subscription ERROR for account id: " + accountId + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private void logInfoOnFinish(String accountId, String id) {
        LOGGER.info("Creating subscription FINISHED for account id " + accountId + ". SUBSCRIPTION_ID: " + id);
    }

    private String getSubscriptionUrl(String scenario) {
        return getScenarioCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_SUBSCRIPTION.getValue();
    }
}

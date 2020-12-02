package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.subscriptions.SubscriptionRequest;
import com.avaya.ecloud.model.response.SubscriptionResponse;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component("subscriptionCommand")
public class SubscriptionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCommand.class);

    @Value("${server.port}")
    private String port;


    @Autowired
    public SubscriptionCommand(@Qualifier("restTemplate") RestTemplate restTemplate, Cache cache) {
        super(cache, restTemplate);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();

        SubscriptionRequest request = ModelUtil.getSubscriptionRequestFromFile(commandData.getResponseData().getRequestBody());
        request.setCallbackURL(getCallbackUrl(scenario));

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, ModelUtil.getRequestHeader(commandData.getResponseData().getAuthToken(), HttpHeaderEnum.CREATE_SUBSCRIPTION));

        try {
            logInfoOnStart(scenario);
            SubscriptionResponse response = getRestTemplate().postForObject(getSubscriptionUrl(scenario), entity, SubscriptionResponse.class);
            getCache().saveSubscriptionId(response.getId());
            logInfoOnFinish(scenario, response.getId());
        } catch (Exception e) {
            logError(scenario, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getCallbackUrl(String scenarioName) {
        String subscriptionUrl = getCache().getSubscriptionUrl(scenarioName);
        StringBuilder builder = new StringBuilder();
        String hostName = null;

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage());
        }

        builder.append("http://").append(hostName).append(":").append(port).append(subscriptionUrl);
        return builder.toString();
    }

    private void logInfoOnStart(String scenario) {
        LOGGER.info("Creating subscription STARTED for scenario: " + scenario);
    }

    private void logError(String scenario, Exception e) {
        LOGGER.error("Creating subscription ERROR for scenario: " + scenario + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private void logInfoOnFinish(String scenario, String id) {
        LOGGER.info("Creating subscription FINISHED for scenario " + scenario + ". SUBSCRIPTION_ID: " + id);
    }

    private String getSubscriptionUrl(String scenario) {
        return getCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_SUBSCRIPTION.getValue();
    }
}

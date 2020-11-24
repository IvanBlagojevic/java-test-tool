package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.subscriptions.EventSubscriptionRequest;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component("eventSubscriptionCommand")
public class EventSubscriptionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventSubscriptionCommand.class);

    @Autowired
    public EventSubscriptionCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String sessionId = getResponseCache().getSessionIds(scenario).get(0);

        HttpHeaders headers = ModelUtil.getRequestHeader(getResponseCache().getSessionToken(scenario), HttpHeaderEnum.EVENT_SUBSCRIPTION);

        String url = getResponseCache().getEventsUri(scenario);
        EventSubscriptionRequest request = ModelUtil.getEventSubscriptionRequestFromFile("subscribeEvents.json");
        request.setSessionId(sessionId);

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, headers);

        try {
            logInfoOnStart(sessionId);
            ResponseEntity<String> response = getRestTemplate().postForEntity(url, entity, String.class);
            logInfoOnFinish(sessionId, ModelUtil.getEventSubscriptionResponse(response.getBody()).getSubscribedEvents().getEvents());
        } catch (Exception e) {
            logError(sessionId, e);
        }
    }

    private void logInfoOnStart(String sessionId) {
        LOGGER.info("Event subscription STARTED for session id " + sessionId);
    }

    private void logError(String sessionId, Exception e) {
        LOGGER.error("Event subscription ERROR for session id: " + sessionId + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private void logInfoOnFinish(String sessionId, List<String> events) {
        LOGGER.info("Event subscription FINISHED for session id " + sessionId + ". SUBSCRIBED_EVENTS: " + events);
    }
}

package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
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
    public EventSubscriptionCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        ResponseData responseData = commandData.getResponseData();
        String sessionId = responseData.getSessionId();

        HttpHeaders headers = ModelUtil.getRequestHeader(responseData.getSessionToken(), HttpHeaderEnum.EVENT_SUBSCRIPTION);

        EventSubscriptionRequest request = ModelUtil.getEventSubscriptionRequestFromFile("subscribeEvents.json");
        request.setSessionId(sessionId);

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, headers);

        try {
            logInfoOnStart(sessionId);

            ResponseEntity<String> response = getRestTemplate().postForEntity(responseData.getResourceData().getEventsUri(),
                    entity,
                    String.class);

            logInfoOnFinish(sessionId, ModelUtil.getEventSubscriptionResponse(response.getBody()).getSubscribedEvents().getEvents());
            executeNext(updateNextCommandData(responseData));
        } catch (Exception e) {
            logError(sessionId, e);
            throw new RuntimeException(e.getMessage());
        }

    }


    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(responseData);
        return data;
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
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

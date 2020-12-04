package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("eventsUnsubscribeCommand")
public class UnsubscribeEventsCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnsubscribeEventsCommand.class);

    @Autowired
    public UnsubscribeEventsCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void execute(CommandData commandData) {
        ResponseData responseData = commandData.getResponseData();
        String sessionId = responseData.getSessionId();

        HttpHeaders headers = ModelUtil.getRequestHeader(responseData.getSessionToken(), HttpHeaderEnum.UNSUBSCRIBE_EVENTS);
        HttpEntity<?> request = new HttpEntity<>(headers);

        StringBuilder builder = new StringBuilder(responseData.getResourceData().getEventsUri());
        builder.append("?sessionId=");
        builder.append(sessionId);

        try {
            getRestTemplate().exchange(builder.toString(), HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(sessionId);
            executeNext(getUpdatedCommandData(commandData));
        } catch (Exception e) {
            logError(sessionId, e);
        }
    }


    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    private void logInfoOnFinish(String sessionId) {
        LOGGER.info("Event unsubscribe FINISHED for session id " + sessionId);
    }

    private void logError(String sessionId, Exception e) {

    }
}

package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.response.ResponseData;
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
        String scenario = commandData.getParent();
        ResponseData responseData = commandData.getResponseData();
        String sessionId = responseData.getSessionId();

        String url = responseData.getEventsUri();

        HttpHeaders headers = ModelUtil.getRequestHeader(responseData.getSessionToken(), HttpHeaderEnum.UNSUBSCRIBE_EVENTS);
        HttpEntity<?> request = new HttpEntity<>(headers);

        StringBuilder builder = new StringBuilder(url);
        builder.append("?sessionId=");
        builder.append(sessionId);

        try {
            getRestTemplate().exchange(builder.toString(), HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(sessionId);
            executeNext(updateNextCommandData(responseData));
        } catch (Exception e) {
            logError(sessionId, e);
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

    private void logInfoOnFinish(String sessionId) {
        LOGGER.info("Event unsubscribe FINISHED for session id " + sessionId);
    }

    private void logError(String sessionId, Exception e) {

    }
}
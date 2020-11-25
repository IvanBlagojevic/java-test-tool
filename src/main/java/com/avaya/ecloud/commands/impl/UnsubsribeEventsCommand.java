package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("eventsUnsubscribeCommand")
public class UnsubsribeEventsCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnsubsribeEventsCommand.class);

    public UnsubsribeEventsCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String sessionId = getResponseCache().getSessionIds(scenario).get(0);
        String url = getResponseCache().getEventsUri(scenario);

        HttpHeaders headers = ModelUtil.getRequestHeader(getResponseCache().getSessionToken(scenario), HttpHeaderEnum.UNSUBSCRIBE_EVENTS);
        HttpEntity<?> request = new HttpEntity<>(headers);

        StringBuilder builder = new StringBuilder(url);
        builder.append("?sessionId=");
        builder.append(sessionId);

        try {
            getRestTemplate().exchange(builder.toString(), HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(sessionId);
        } catch (Exception e) {
            logError(sessionId, e);
        }
    }

    private void logInfoOnFinish(String sessionId) {
        LOGGER.info("Event unsubscribe FINISHED for session id " + sessionId);
    }

    private void logError(String sessionId, Exception e) {

    }
}

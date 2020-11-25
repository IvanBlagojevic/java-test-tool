package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("deleteSessionCommand")
public class DeleteSessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSessionCommand.class);

    public DeleteSessionCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String sessionEndpoint = getScenarioCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_SESSION.getValue();
        String sessionId = getResponseCache().getSessionIds(scenario).get(0);

        HttpHeaders headers = ModelUtil.getRequestHeader(getResponseCache().getAuthToken(scenario), HttpHeaderEnum.DELETE_SESSION);
        HttpEntity<?> request = new HttpEntity<>(headers);

        StringBuilder builder = new StringBuilder(sessionEndpoint);
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

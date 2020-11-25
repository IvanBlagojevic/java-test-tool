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


@Component("terminateClientCommand")
public class TerminateClientCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminateClientCommand.class);

    public TerminateClientCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String terminateClientUri = getResponseCache().getTerminateClientUri(scenario);

        HttpHeaders headers = ModelUtil.getRequestHeader(getResponseCache().getSessionToken(scenario), HttpHeaderEnum.TERMINATE_CLIENT);
        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            getRestTemplate().exchange(terminateClientUri, HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(terminateClientUri);
        } catch (Exception e) {
            logError(terminateClientUri, e);
        }
    }

    private void logInfoOnFinish(String terminateClientUri) {
        LOGGER.info("Terminate client FINISHED for client uri " + terminateClientUri);
    }

    private void logError(String sessionId, Exception e) {

    }
}


package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.endCall.CallAction;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("endEstablishedCallCommand")
public class EndEstablishedCallCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndEstablishedCallCommand.class);

    public EndEstablishedCallCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String url = getResponseCache().getCallsUri(scenario);
        String callId = getResponseCache().getCallId(scenario);

        CallAction request = ModelUtil.getCallActionRequestFromFile((String) commandData.getConfig().get("config"));
        String sessionToken = getResponseCache().getSessionToken(scenario);
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(sessionToken, HttpHeaderEnum.END_CALL);

        // TODO We should refactor this to use Spring Rest Template
        // TODO Instead of StringBuilder approach
        StringBuilder builder = new StringBuilder(url);
        builder.append("/");
        builder.append(callId);

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            logInfoOnStart(callId);
            getRestTemplate().postForObject(builder.toString(), entity, String.class);
            logInfoOnFinish(callId);
        } catch (Exception e) {
            logError(callId, e);
        }
    }

    private void logInfoOnStart(String callId) {
        LOGGER.info("Started to end established call for call id " + callId);
    }

    private void logError(String callId, Exception e) {
        LOGGER.error("Started to end established call ERROR for call id: " + callId + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private void logInfoOnFinish(String callId) {
        LOGGER.info("Started to end established call FINISHED for call id " + callId);
    }
}

package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.utils.ModelUtil;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.startAudioCall.AudioCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.avaya.ecloud.utils.ModelUtil.getCallIdFromResponse;

@Component("createNewCallCommand")
public class CreateNewCallCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNewCallCommand.class);

    @Autowired
    public CreateNewCallCommand(ScenarioCache scenarioCache, ResponseCache responseCache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String url = getResponseCache().getCallsUri(scenario);
        AudioCall request = ModelUtil.getAudioCallRequestFromFile("startAudioCall.json");
        String sessionId = getResponseCache().getSessionIds(scenario).get(0);
        request.setSessionId(sessionId);

        String sessionToken = getResponseCache().getSessionToken(scenario);
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(sessionToken, HttpHeaderEnum.CREATE_NEW_CALL);

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            logInfoOnStart(sessionId);
            String response = getRestTemplate().postForObject(url, entity, String.class);
            String callId = getCallIdFromResponse(response);
            getResponseCache().saveCallId(scenario, callId);
            logInfoOnFinish(sessionId, callId);
        } catch (Exception e) {
            logError(sessionId, e);
        }
    }

    private void logInfoOnStart(String sessionId) {
        LOGGER.info("Creating new call STARTED for session id " + sessionId);
    }

    private void logError(String sessionId, Exception e) {
        LOGGER.error("Creating new call ERROR for session id: " + sessionId + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private void logInfoOnFinish(String sessionId, String id) {
        LOGGER.info("Creating new call FINISHED for session id " + sessionId + ". CALL_ID: " + id);
    }
}

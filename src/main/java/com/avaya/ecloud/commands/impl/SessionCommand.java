package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.session.CreateSessionRequest;
import com.avaya.ecloud.model.response.session.SessionResponse;
import com.avaya.ecloud.model.response.session.SessionToken;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component("sessionCommand")
public class SessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionCommand.class);

    @Autowired
    public SessionCommand(ScenarioCache scenarioCache, ResponseCache responseCache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {

        String scenario = commandData.getParent();
        String accountId = getScenarioCache().getAccountId(scenario);
        String authToken = getResponseCache().getAuthToken(scenario);
        List<String> confIds = getResponseCache().getConferenceIds(scenario);

        int rampTime = getScenarioCache().getRampTime(scenario);
        int sessionCounter = getScenarioCache().getSessionCounter(scenario);
        int timeRate = rampTime / sessionCounter;

        CreateSessionRequest sessionRequest = ModelUtil.getCreateSessionRequestFromFile((String) commandData.getConfig().get("config"));

        for (String confId : confIds) {
            sessionRequest.getOperation().getJoin().setResourceId(confId);
            HttpEntity<String> entity = ModelUtil.getEntityFromObject(sessionRequest, ModelUtil.getRequestHeader(authToken, HttpHeaderEnum.CREATE_SESSION));
            for (int i = 0; i < sessionCounter; i++) {
                createSessions(entity, scenario, accountId);
            }
        }

    }

    private void createSessions(HttpEntity<String> entity, String scenario, String accountId) {
        try {
            logInfoOnStart(accountId);
            getCreateSessionUrl(scenario);

            SessionResponse response = getRestTemplate().postForObject(getCreateSessionUrl(scenario), entity, SessionResponse.class);
            SessionToken sessionToken = ModelUtil.getSessionTokenFromResponse(response);

            cacheResponseData(scenario, sessionToken);

            logInfoOnFinish(accountId, sessionToken.getSessionId());
        } catch (Exception e) {
            logError(e);
        }
    }

    private void cacheResponseData(String scenario, SessionToken token) {
        ResponseCache responseCache = getResponseCache();
        responseCache.saveSessionId(scenario, token.getSessionId());
        responseCache.saveSessionToken(scenario, token.getSessionToken());
        responseCache.saveUserAgentURL(scenario, token.getUserAgentURL());
    }

    private void logInfoOnStart(String accountId) {
        LOGGER.info("Creating sessions STARTED for user " + accountId);
    }

    private void logInfoOnFinish(String accountId, String id) {
        LOGGER.info("Creating sessions FINISHED for user " + accountId + " SESSION_ID: " + id);
    }

    private void logError(Exception e) {
        LOGGER.error("Creating sessions ERROR . ERROR MESSAGE: " + e.getMessage(), e);
    }

    private String getCreateSessionUrl(String scenario) {
        return getScenarioCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_SESSION.getValue();
    }
}

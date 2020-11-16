package com.example.yaml.commands.impl;

import com.example.yaml.cache.ResponseCache;
import com.example.yaml.cache.ScenarioCache;
import com.example.yaml.commands.Command;
import com.example.yaml.commands.utils.CommandUtil;
import com.example.yaml.model.command.CommandData;
import com.example.yaml.model.enums.ApiUrlEnum;
import com.example.yaml.model.enums.HttpHeaderEnum;
import com.example.yaml.model.requests.CreateSessionRequest;
import com.example.yaml.model.response.SessionResponse;
import com.example.yaml.model.response.SessionToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component("sessionCommand")
public class SessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionCommand.class);

    @Autowired
    public SessionCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {

        String scenario = commandData.getParent();
        String accountId = getScenarioCache().getAccountId(scenario);
        String authToken = getResponseCache().getAuthToken(scenario);
        List<String> confId = getResponseCache().getConferenceIds(scenario);

        CreateSessionRequest sessionRequest = CommandUtil.getCreateSessionRequestFromFile((String) commandData.getConfig().get("config"));
        sessionRequest.getOperation().getJoin().setResourceId(confId.get(0));


        HttpEntity<String> entity = CommandUtil.getEntityFromObject(sessionRequest, CommandUtil.getRequestHeader(authToken, HttpHeaderEnum.CREATE_SESSION));
        createSessions(entity, scenario, accountId);
    }

    private void createSessions(HttpEntity<String> entity, String scenario, String accountId) {
        try {
            logInfoOnStart(accountId);
            getCreateSessionUrl(scenario);

            SessionResponse response = getRestTemplate().postForObject(getCreateSessionUrl(scenario), entity, SessionResponse.class);
            SessionToken sessionToken = CommandUtil.getSessionTokenFromResponse(response);

            cacheResponseData(scenario, sessionToken);

            logInfoOnFinish(accountId, sessionToken.getSessionId());
        } catch (Exception e) {
            logError(e);
        }
    }

    private void cacheResponseData(String scenario, SessionToken token) {
        getResponseCache().saveSessionId(scenario, token.getSessionId());
        getResponseCache().saveSessionToken(scenario, token.getSessionToken());
        getResponseCache().saveUserAgentURL(scenario, token.getUserAgentURL());
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

package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.session.CreateSessionRequest;
import com.avaya.ecloud.model.response.ResponseData;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("sessionCommand")
public class SessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionCommand.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Autowired
    public SessionCommand(Cache cache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {

        String scenario = commandData.getParent();
        String authToken = getCache().getAuthToken(scenario);
        List<String> confIds = getCache().getConferenceIds(scenario);

        double rampTime = getCache().getRampTime(scenario);
        double numberOfSessions = getCache().getSessionCounter(scenario);
        double numberOfConferences = getCache().getConferenceCounter(scenario);
        long timeRate = (long) (rampTime / numberOfSessions * 1000);
        int sessionCounter = (int) (numberOfSessions / numberOfConferences);

        CreateSessionRequest sessionRequest = ModelUtil.getCreateSessionRequestFromFile((String) commandData.getConfig().get("config"));

        for (String confId : confIds) {
            sessionRequest.getOperation().getJoin().setResourceId(confId);
            HttpEntity<String> entity = ModelUtil.getEntityFromObject(sessionRequest,
                    ModelUtil.getRequestHeader(authToken, HttpHeaderEnum.CREATE_SESSION));
            for (int i = 0; i < sessionCounter; i++) {
                executorService.submit(() -> createSessions(entity, scenario, authToken));
                sleep(timeRate);
            }
        }
    }

    private void sleep(long milliseconds) {
        try {
            LOGGER.info("Waiting for " + milliseconds + " milliseconds before creating another session.");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void setNext(Command command) {
        setNextCommand(command);
    }

    private void createSessions(HttpEntity<String> entity, String scenario, String authToken) {
        try {
            logInfoOnStart(scenario);
            SessionResponse response = getRestTemplate().postForObject(getCreateSessionUrl(scenario), entity, SessionResponse.class);
            SessionToken sessionToken = ModelUtil.getSessionTokenFromResponse(response);
            logInfoOnFinish(scenario, sessionToken.getSessionId());
            executeNext(getUpdatedCommandData(sessionToken, authToken));
        } catch (Exception e) {
            logError(scenario, e);
        }
    }

    private CommandData getUpdatedCommandData(SessionToken sessionToken, String accessToken) {
        CommandData nextCommandData = getNextCommandData();
        return new CommandData(nextCommandData.getName(),
                nextCommandData.getParent(),
                new ResponseData(sessionToken.getSessionToken(), sessionToken.getSessionId(), sessionToken.getUserAgentURL(), accessToken),
                nextCommandData.getConfig());
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    private void logInfoOnStart(String scenario) {
        LOGGER.info("Creating sessions STARTED for scenario " + scenario);
    }

    private void logInfoOnFinish(String scenario, String id) {
        LOGGER.info("Creating sessions FINISHED for scenario " + scenario + " SESSION_ID: " + id);
    }

    private void logError(String scenario, Exception e) {
        LOGGER.error("Creating sessions ERROR for scenario " + scenario + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private String getCreateSessionUrl(String scenario) {
        return getCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_SESSION.getValue();
    }
}

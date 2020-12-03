package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.startAudioCall.AudioCall;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.avaya.ecloud.utils.ModelUtil.getCallIdFromResponse;

@Component("createNewCallCommand")
public class CreateNewCallCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNewCallCommand.class);

    @Autowired
    public CreateNewCallCommand(Cache cache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        ResponseData responseData = commandData.getResponseData();
        String sessionId = responseData.getSessionId();

        AudioCall request = ModelUtil.getAudioCallRequestFromFile("startAudioCall.json");
        request.setSessionId(sessionId);

        String sessionToken = responseData.getSessionToken();
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(sessionToken, HttpHeaderEnum.CREATE_NEW_CALL);

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            logInfoOnStart(sessionId);

            ResponseEntity<String> response = getRestTemplate().exchange(responseData.getResourceData().getCallsUri(),
                    HttpMethod.POST,
                    entity,
                    String.class);

            String callId = getCallIdFromResponse(response.getBody());
            responseData.setCallId(callId);
            logInfoOnFinish(sessionId, callId);
            executeNext(updateNextCommandData(responseData));
        } catch (Exception e) {
            logError(sessionId, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(responseData);
        return data;
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
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

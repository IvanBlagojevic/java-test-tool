package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.endCall.CallAction;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("endEstablishedCallCommand")
public class EndEstablishedCallCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndEstablishedCallCommand.class);

    @Autowired
    public EndEstablishedCallCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    @Override
    public void execute(CommandData commandData) {
        ResponseData responseData = commandData.getResponseData();
        String callId = responseData.getCallId();

        CallAction request = ModelUtil.getCallActionRequestFromFile("endCall.json");
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(responseData.getSessionToken(), HttpHeaderEnum.END_CALL);

        // TODO We should refactor this to use Spring Rest Template
        // TODO Instead of StringBuilder approach
        StringBuilder builder = new StringBuilder(responseData.getResourceData().getCallsUri());
        builder.append("/");
        builder.append(callId);

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            logInfoOnStart(callId);
            getRestTemplate().postForEntity(builder.toString(), entity, String.class);
            logInfoOnFinish(callId);
            executeNext(updateNextCommandData(responseData));
        } catch (Exception e) {
            logError(callId, e);
        }

    }

    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(responseData);
        return data;
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
